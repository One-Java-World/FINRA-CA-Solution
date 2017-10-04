
package com.edom.mesfin.solution.rest;

import com.edom.mesfin.solution.entity.Document;
import com.edom.mesfin.solution.pojo.DocMetadata;
import com.edom.mesfin.solution.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;
//import javax.annotation.Resource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ByteArrayResource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    
    private final String FILE_DIR = System.getProperty("user.home") + File.separator + "file_dir";
    
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
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
        file = new File(FILE_DIR + File.separator + uuid + "." + ext);
        try {                        
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        } catch (IOException ex) {
            errMsg = "Error persisting the file stream to local file.";
            LOG.debug(errMsg);
            return ResponseEntity.badRequest().body(errMsg);
        }
        
        return ResponseEntity.ok().body("Document Uploaded successfully! UUID:" + uuid);        
    }

    @Override
    public ResponseEntity<Resource> getFileByuuidSysName(String uuid, String sysName) {
        String errMsg;
        ////Return file. Not requested.
        
        Document doc = docServImpl.getDocumentByUuid(uuid);
        if(doc == null){
            errMsg = "Document could not be found for the given UUID: " + uuid;
            LOG.debug(errMsg);
            return ResponseEntity.notFound().header("ErrorMsg", errMsg).build();
        }
        
        String ext = FilenameUtils.getExtension(doc.getFileName());
        File file = new File(FILE_DIR + File.separator + uuid + "." + ext);
        
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException ex) {
            errMsg = "Document meta-data was found, but the actual file could not be retrieved from the local file system. UUID: " + uuid;
            LOG.debug(errMsg);
            return ResponseEntity.notFound().header("ErrorMsg", errMsg).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("UUID", doc.getUuid());
        headers.add("FileName", doc.getFileName());
        headers.add("SystemName", doc.getSysName());
        headers.add("Created_Date", doc.getCreatedDate().toString());
        headers.add("Content-Disposition", "attachment; filename=\"" + doc.getFileName() + "\"");
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);        
    }

}
