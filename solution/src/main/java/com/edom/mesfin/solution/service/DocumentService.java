
package com.edom.mesfin.solution.service;

import com.edom.mesfin.solution.entity.Document;
import com.edom.mesfin.solution.repository.DocumentRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for Document related operations.
 * 
 * @author Edom.Mesfin
 */
@Service
public class DocumentService {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);
    
    @Autowired
    private DocumentRepository docRepository;
    
    /* Test data - List */
    private List<Document> docs = new ArrayList<>(Arrays.asList(
            new Document("testData.pdf", "EDOCS"),
            new Document("performanceReport.docx", "GS"),
            new Document("InformData.pdf", "EPIC"),
            new Document("GrantSolutions.doc", "GS"),
            new Document("S3FileTest.pdf", "DocMngt")));
    
    
    
    public void createOrupdate(Document entity){
        docRepository.save(entity);
    }
    
    public Document getDocumentByUuid(String uuid){
        /* Using the test data */
        //return docs.stream().filter(t -> t.getUuid().equals(uuid)).findFirst().get();
        
        /* Using the embedded database data entry */
        return docRepository.findByUuid(uuid);  //// OR, docRepository.findOne(uuid);
    }
    
    public void createOrupdateWithFile(Document doc, String uuid, MultipartFile uploadFile, final String FILE_DIR) throws IOException{        
        this.createOrupdate(doc);
        
        ////Then Save file to local file system using UUID and file extension for later retrival.                
        File file;        
        String ext = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
        file = new File(FILE_DIR + File.separator + uuid + "." + ext);
        
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
    }
    
    public void updateDocumentStorageFlag(String uuid, boolean storedInS3) throws Exception {
        
        if(StringUtils.isBlank(uuid)){
            String errMsg = "UUID can not be null or empity. Update request failed.";
            LOG.debug(errMsg);
            throw new Exception(errMsg);
        }
        
        LOG.debug("... Updating Document (" + uuid + "), with S3 Storage Status: " + storedInS3);
        Document doc = this.getDocumentByUuid(uuid);
        if (doc == null) {
            String errMsg = "Document with UUID: " + uuid + ", can not be found. Update request failed.";
            LOG.debug(errMsg);
            throw new Exception(errMsg);
        }

        doc.setStoredInS3(storedInS3);

        doc.setUpdatedDate(new Date());
        this.createOrupdate(doc);
                       
        LOG.info("Document with UUID: " + uuid + ", has been successfuly updated.");
    }
    
    public List<Document> getAllDocMetaData(){
        /* Using the test data */
        //return docs;
        
        /* Using the embedded database data entry */
        List<Document> allDocuments = new ArrayList<>();
        docRepository.findAll().forEach(allDocuments::add); ////Using method reference.
        return allDocuments;
    }
    
    public boolean addMetaData(Document docEntry){
        /* Using the test data */
        //docs.add(docEntry);
        
        /* Using the embedded database data entry */
        this.createOrupdate(docEntry);
        
        return true;
    }
    
    public List<Document> getMetaDataBySysName(String systemName){
        /* Using the test data */
        //return docs.stream().filter(t -> t.getSysName().equals(systemName)).collect(Collectors.toCollection(ArrayList::new));
        
        /* Using the embedded database data entry */
        return docRepository.findBySysName(systemName);
    }
    
}
