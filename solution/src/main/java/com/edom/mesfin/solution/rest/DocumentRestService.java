
package com.edom.mesfin.solution.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public interface DocumentRestService {
    
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
     * Test, Browser: http://localhost:8080/getDocumentByuuidSysName?uuid=1Id-23-xxx9,sysName=unity
     * 
     * @param uuiD
     * @param sysName
     * @return
     */
    @RequestMapping(
            value = "/getDocumentByuuidSysName",
            method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> getFileByuuidSysName(@RequestPart("uuid") String uuid, @RequestPart("sysName") String sysName);

}
