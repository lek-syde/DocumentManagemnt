package com.nphcda.application.data.entity;

import com.nphcda.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Arrays;

@Entity
public class Document extends AbstractEntity {

    private String notes;
    private String year;
    private String month;

    private String fileName;

    private String fileType;


    public Document(String notes, String year, String month, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }


    public Document() {
    }

    @Lob
    private byte[] data;



    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "Document{" +
                "notes='" + notes + '\'' +
                "id"+ getId()+
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
