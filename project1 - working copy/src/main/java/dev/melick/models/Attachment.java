package dev.melick.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/*
*  @Entity tells hibertnate that this class corresponds to a table in the DB
*  @Table allows us to provide extra information for our table, mainly the name of the table
* */

@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachmentGenerator")
    @SequenceGenerator(name = "attachmentGenerator", sequenceName = "attachments_att_id_seq", allocationSize = 1)
    @Column(name = "att_id")
    private Integer attId;

    @Column(name = "att_by", nullable = false)
    private Integer attBy;

    @Column(name = "file_name")
    private String fileName;

    @Column(nullable = false)
    private String file;

    @Column(name = "file_ext")
    private String fileExt;

    @Column(name = "att_date")
    @CreationTimestamp
    private Date attDate;

    public Attachment(){}

    public Attachment(Integer attId, Integer attBy, String fileName, String file, String fileExt, Date attDate) {
        this.attId = attId;
        this.attBy = attBy;
        this.fileName = fileName;
        this.file = file;
        this.fileExt = fileExt;
        this.attDate = attDate;
    }

    public Integer getAttId() {
        return attId;
    }

    public void setAttId(Integer attId) {
        this.attId = attId;
    }

    public Integer getAttBy() {
        return attBy;
    }

    public void setAttBy(Integer attBy) {
        this.attBy = attBy;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getAttDate() {
        return attDate;
    }

    public void setAttDate(Date attDate) {
        this.attDate = attDate;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attId=" + attId +
                ", attBy=" + attBy +
                ", fileName='" + fileName + '\'' +
                ", file='" + file + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", attDate=" + attDate +
                '}';
    }
}
