
package com.edom.mesfin.solution.rest;

import com.edom.mesfin.solution.entity.Document;
import com.edom.mesfin.solution.pojo.DocMetadata;
import com.edom.mesfin.solution.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation Class
 * 
 * @author Edom.Mesfin
 */
@Service("documentRestService")
public class DocumentRestServiceImpl implements DocumentRestService{

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DocumentRestServiceImpl.class);
    
    @Autowired
    private DocumentService docServImpl;
    
    @Override
    public ResponseEntity<String> uploadDocument(MultipartFile uploadFile, String documentMetadata) {
        DocMetadata metaData = null;
        String errMsg;
        try {
            metaData = new ObjectMapper().readValue(documentMetadata, DocMetadata.class);
        } catch (IOException ex) {
            errMsg = "Issue happened while extracting file meta data. Upload request failed.";
            LOG.debug(errMsg, ex);
            return ResponseEntity.badRequest().body(ex.getMessage());            
        }
        if(uploadFile == null || metaData == null){
            errMsg = "Document or Meta Data can not be null or empity. Upload request failed.";
            LOG.debug(errMsg);
            return ResponseEntity.badRequest().body(errMsg);
        }
        else if(!uploadFile.getOriginalFilename().equals(metaData.getFileName())){
            errMsg = "Document name should match with the meta data entry!. Upload request failed.";
            LOG.debug(errMsg);
            return ResponseEntity.badRequest().body(errMsg);
        }
        
        String uuid = (StringUtils.isBlank(metaData.getUuid()))? UUID.randomUUID().toString() : metaData.getUuid();
        
        Document doc = new Document();
        doc.setUuid(uuid);
        doc.setFileName(metaData.getFileName());
        doc.setSysName(metaData.getSystemName());
        doc.setStoredInS3(metaData.isStoredInS3());
        doc.setCreatedDate(new Date());
        doc.setUpdatedDate(new Date());

        docServImpl.createOrupdate(doc);

        ////Then Save file to local file system using UUID and file extension for later retrival.                
        File file;
        try {
            file = File.createTempFile(uuid, null);
            file.deleteOnExit();
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        } catch (IOException ex) {
            errMsg = "Error persisting the file stream to local file.";
            LOG.debug(errMsg);
            return ResponseEntity.badRequest().body(errMsg);
        }
        
        return ResponseEntity.ok().body("Document Uploaded successfully! UUID:" + uuid);        
    }

    @Override
    public ResponseEntity<String> getFileByuuidSysName(String uuid, String sysName) {
        
        ////Return file. Not requested.
        // ...
        return ResponseEntity.ok().body("Document Uploaded successfully! UUID:" + uuid);
        
    }

}
