package com.example.parkinglotintellij.common;

import java.io.Serializable;
import java.util.Objects;

public class PhotoDetails implements Serializable {
    private final Integer id;
    private final String filename;
    private final String fileType;
    private final byte[] fileContent;

    public PhotoDetails(Integer id, String filename, String fileType, byte[] fileContent) {
        this.id = id;
        this.filename = filename;
        this.fileType = fileType;
        this.fileContent = fileContent;
    }

    public Integer getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoDetails entity = (PhotoDetails) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.filename, entity.filename) &&
                Objects.equals(this.fileType, entity.fileType) &&
                Objects.equals(this.fileContent, entity.fileContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filename, fileType, fileContent);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "filename = " + filename + ", " +
                "fileType = " + fileType + ", " +
                "fileContent = " + fileContent + ")";
    }
}
