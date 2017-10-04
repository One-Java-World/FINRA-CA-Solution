
package com.edom.mesfin.solution.service;

import com.edom.mesfin.solution.entity.Document;
import com.edom.mesfin.solution.repository.DocumentRepository;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

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
    
    
    public void createOrupdate(Document entity){
        docRepository.save(entity);
    }
    
    public Document getDocumentByUuid(String uuid){
        return docRepository.findByUuid(uuid);
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
    
    
}
