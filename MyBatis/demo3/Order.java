package com.mybatis.demo3;

import java.util.Date;

public class Order {
    private Integer id;
    private Integer user_id;
    private String number;
    private Date createtime;
    private String note;
    private User user;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", number='" + number + '\'' +
                ", createtime=" + createtime +
                ", note='" + note + '\'' +
                ", user=" + user.getUsername() +'\''+ user.getAddress() +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getNumber() {
        return number;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public String getNote() {
        return note;
    }

    public User getUser() {
        return user;
    }
}
