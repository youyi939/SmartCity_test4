package com.example.smartcity_test4.ui.home.pojo;

public class Item_service {
    private int id;
    private String serviceName;
    private String url;

    public Item_service(int id, String serviceName, String url) {
        this.id = id;
        this.serviceName = serviceName;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Item_service{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
