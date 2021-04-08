package com.example.smartcity_test4.ui.home.pojo;

public class Img {
    private int id;
    private String url;
    private String type;
    private String sort;

    @Override
    public String toString() {
        return "Img{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Img(int id, String url, String type, String sort) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.sort = sort;
    }
}
