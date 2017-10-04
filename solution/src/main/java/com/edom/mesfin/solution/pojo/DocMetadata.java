
package com.edom.mesfin.solution.pojo;

import java.io.Serializable;

/**
 * Document meta-data pojo
 * 
 * @author Edom.Mesfin
 */
public class DocMetadata implements Serializable {
    
    public DocMetadata(){}
    
    private String uuid;
    private String systemName;
    private String fileName;
    private boolean storedInS3;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isStoredInS3() {
        return storedInS3;
    }

    public void setStoredInS3(boolean storedInS3) {
        this.storedInS3 = storedInS3;
    }
    
}
