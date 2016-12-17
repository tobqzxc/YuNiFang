package com.example.qwe.yunifang.sql;

/**
 * Created by qwe on 2016/12/14.
 */
public class Product {

    private String name;
    private String price;
    private String id;
    private String url;
    private int num;
    private boolean Checked;

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }

    public Product(String name, String price, String id, String url, int num) {
        this.name = name;
        this.price = price;
        this.id = id;
        this.url = url;
        this.num = num;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
