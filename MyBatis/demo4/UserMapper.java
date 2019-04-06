package com.mybatis.demo1.mapper;

import com.mybatis.demo1.pojo.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户持久化接口
 */
@Component(value = "UserMapper")
public interface UserMapper {
    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 通过包装类查询用户
     * @param user
     * @return
     */
    User getUserByUser(User user);

    /**
     * 查询所有用户
     * @return
     */
    List<User> getUserList();

    /**
     * 插入用户
     * @param user
     */
    void insertUser(User user);
}
