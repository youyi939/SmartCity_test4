package com.example.smartcity_test4.ui.home.pojo;

import java.util.List;

public class Item {
    private int code;
    private String label;
    private List<ItemData> dataList;

    @Override
    public String toString() {
        return "Item{" +
                "code=" + code +
                ", label='" + label + '\'' +
                ", dataList=" + dataList +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ItemData> getDataList() {
        return dataList;
    }

    public void setDataList(List<ItemData> dataList) {
        this.dataList = dataList;
    }

    public Item(int code, String label, List<ItemData> dataList) {
        this.code = code;
        this.label = label;
        this.dataList = dataList;
    }
}
