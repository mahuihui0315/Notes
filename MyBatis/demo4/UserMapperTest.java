package com.mybatis.demo1.test;

import com.mybatis.demo1.mapper.UserMapper;
import com.mybatis.demo1.pojo.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void test(){
        User user=userMapper.getUserById(1);
        System.out.println(user.toString());
    }

    private ApplicationContext applicationContext;

    @Before
    public void init(){
        applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @Test
    /**
     * 按照id查询测试
     */
    public void test01(){
        UserMapper userMapper=applicationContext.getBean(UserMapper.class);
        User user=userMapper.getUserById(1);
        System.out.println(user.toString());
    }

    @Test
    /**
     * 通过包装类查询
     * 动态查询的if标签只能使用在parameterType为对象的时候
     */
    public void test02(){
        UserMapper userMapper=applicationContext.getBean(UserMapper.class);
        User test=new User();
        test.setId(1);
        User user=userMapper.getUserByUser(test);
        System.out.println(user.toString());
    }

    @Test
    /**
     * 插入测试
     */
    public void test03(){
        UserMapper userMapper=applicationContext.getBean(UserMapper.class);
        User user=new User();
        user.setUsername("2B");
        user.setGender("woman");
        user.setPhone("pod 042");
        user.setAddress("YoRHa");
        userMapper.insertUser(user);
        System.out.println(user.getId());
    }

    @Test
    /**
     * 查询所有用户
     */
    public void test04(){
        UserMapper userMapper=applicationContext.getBean(UserMapper.class);
        List<User> users=userMapper.getUserList();
        for (User user:users){
            System.out.println(user.toString());
        }
    }
}
