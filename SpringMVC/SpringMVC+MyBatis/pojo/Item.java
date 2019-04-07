package com.springMVC.demo1.pojo;

import java.util.Date;

public class Item {
    private int id;
    private String name;
    private Date createTime;
    private double price;
    private String detail;

    public Item(){

    }

    public Item(int id, String name, double price, Date createTime, String detail) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.price = price;
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public double getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }
}
