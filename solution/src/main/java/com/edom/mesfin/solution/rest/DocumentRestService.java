
package com.edom.mesfin.solution.rest;

import com.edom.mesfin.solution.entity.Document;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * REST Interface for document Mngt.
 * 
 * @author Edom.Mesfin
 */
@RestController
@RequestMapping(value = DocumentRestService.ROOT)
public interface DocumentRestService {
    
    String ROOT = "api/docMngt";
    
    /**
     * Uploads document meta-data to embedded h2 DB and the actual file stream to local file system .Accepts documentMetadata as
     * JSON, and file as uploadFile.
     *
     * @param uploadFile
     * @param documentMetadata
     * @return UUID of the saved document as confirmation. if UUID is not sent, then the service generates one and sends back that as confirmation.
     */
    @RequestMapping(
            value = "/uploadDocument", 
            method = RequestMethod.POST, 
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadDocument(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestPart("documentMetadata") String documentMetadata);
   
    
    /**
     * Get file from a specific location
     * Test, Browser: http://localhost:8080/api/docMngt/getDocumentByuuidSysName?uuid=69d5e7d1-d79f-48aa-b698-5dd78d54c92b&sysName=DOCM
     * 
     * @param uuid
     * @param sysName
     * @return
     */
    @RequestMapping(
            value = "/getDocumentByuuidSysName",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getFileByuuidSysName(@RequestPart("uuid") String uuid, @RequestPart("sysName") String sysName);

    /**
     * Upload Document meta data.
     * Spring would automatically converts and injects the request body (by default JSON) to the param here.
     * 
     * @param doc
     * @return 
     */
    @RequestMapping(value = "/documentMetaData", method = RequestMethod.POST)
    public String uploadDocMetaData(@RequestBody Document doc);
    
    /** 
     * Returns all the meta data stored form the source, without the file.
     * Defaults are assumed for method(GET), produces(return type - GSON)
     * 
     * Test: http://localhost:8080/api/docMngt/documentMetaData/
     * 
     * @return 
     */
    @RequestMapping("/documentMetaData")
    public List<Document> getAllMetaData();
    
    /**
     * Find Document Meta Data by UUID
     * Test: http://localhost:8080/api/docMngt/documentMetaDatum/502f5779-f87f-4b1f-b4d8-582b96e9f16d
     * 
     * @param uuid
     * @return 
     */
    @RequestMapping("/documentMetaDatum/{uuid}")
    public Document getDocumentMetaData(@PathVariable String uuid);
    
    /**
     * Find Document Meta Data by System Name
     * Test: http://localhost:8080/api/docMngt/documentMetaData/GS
     * 
     * @param sysName
     * @return 
     */
    @RequestMapping("/documentMetaData/{SystemName}")
    public List<Document> getDocumentMetaDataBySysName(@PathVariable("SystemName") String sysName);
    
}
