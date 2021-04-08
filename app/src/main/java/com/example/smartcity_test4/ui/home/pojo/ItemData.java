package com.example.smartcity_test4.ui.home.pojo;

public class ItemData {
    private String title;
    private String content;
    private String url;
    private int viewNumber;
    private String createTime;


    public ItemData(String title, String content, String url, int viewNumber, String createTime) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.viewNumber = viewNumber;
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", viewNumber=" + viewNumber +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
