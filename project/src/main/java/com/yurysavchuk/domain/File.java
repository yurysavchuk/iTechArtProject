package com.yurysavchuk.domain;

public class File {

    public File(){}

    public File(String filename, String path, String dateLoad, String comment){
        this.filename = filename;
        this.path = path;
        this.dateLoad = dateLoad;
        this.comment = comment;
    }

    public File(Integer id, String filename,String path,String dateLoad, String comment ) {
        this.id = id;
        this.comment = comment;
        this.dateLoad = dateLoad;
        this.path = path;
        this.filename = filename;
    }

    private String filename;
    private String path;
    private String dateLoad;
    private String comment;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDateLoad(String dateLoad) {
        this.dateLoad = dateLoad;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFilename() {

        return filename;
    }

    public String getPath() {
        return path;
    }

    public String getDateLoad() {
        return dateLoad;
    }

    public String getComment() {
        return comment;
    }
}
