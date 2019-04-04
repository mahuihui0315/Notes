package com.mybatis.demo2;

import com.mybatis.demo1.User;

public interface UserDao {
    public User getUserById(int id);
    public void insertUser(User user);
    public void updateUserById(User user,int id);
    public void deleteUserById(User user,int id);
}
