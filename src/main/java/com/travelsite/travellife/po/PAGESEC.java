package com.travelsite.travellife.po;

import javax.persistence.*;
@Entity
@Table(name = "pagesec")
public class PAGESEC {

    @Id
    @GeneratedValue
    private Integer id;
    private String url;

    private String path;

    private String status;

    private String md5str;

    public PAGESEC() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMd5str() {
        return md5str;
    }

    public void setMd5str(String md5str) {
        this.md5str = md5str;
    }

    @Override
    public String toString() {
        return "PAGE{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", status=" + status +
                '}';
    }
}
