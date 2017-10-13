
package com.edom.mesfin.solution.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

/**
 * Entity Class for the file meta-data persistency using spring data.
 * 
 * @author Edom.Mesfin
 */
@Entity
@Table(name = "DOCUMENT_FINRA")
public class Document {
    
    @Id
    @Column(name = "UUID")
    private String uuid;
    
    private String fileName;
    
    @Column(name = "EXTERNALSYSTEMNAME")
    private String sysName; //// System/Owner/Client name - whoever posted the file should provide a system name as meta-data as well .
    
    @Column(name = "CREATEDATE")
    private Date createdDate;
    
    @Column(name = "UPDATEDATE")
    private Date updatedDate;
    
    @Column(name = "InS3", nullable = true, columnDefinition = "VARCHAR2(3)")
    @Type(type = "yes_no")
    private boolean storedInS3;

    public Document(){}
    public Document(String fileName, String systemName){
        super();
        this.uuid = UUID.randomUUID().toString();
        this.createdDate = this.updatedDate = new Date();
        this.storedInS3 = false;
        this.fileName = fileName;
        this.sysName = systemName;
    }
    
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isStoredInS3() {
        return storedInS3;
    }

    public void setStoredInS3(boolean storedInS3) {
        this.storedInS3 = storedInS3;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
       
    
}
