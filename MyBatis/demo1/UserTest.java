package com.mybatis.demo1;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis基本操作测试
 */
public class UserTest {

    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder=null;
    private InputStream stream=null;
    private SqlSessionFactory sqlSessionFactory=null;
    private SqlSession sqlSession=null;

    @Before
    public void init() throws IOException {
        //创建SqlSessionFactoryBuilder对象
        sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        //创建输入流引入配置文件
        stream=Resources.getResourceAsStream("SqlMapConfig.xml");
        //加载配置文件，创建SqlSessionFactory对象
        sqlSessionFactory=sqlSessionFactoryBuilder.build(stream);
        //创建SqlSession对象
        sqlSession=sqlSessionFactory.openSession();
    }

    @Test
    /**
     * 根据id查询测试
     */
    public void test01(){
        User user=sqlSession.selectOne("user.getUserById",1);
        System.out.println(user.getUsername());

        sqlSession.close();
    }

    @Test
    /**
     * 根据姓名查询测试
     */
    public void test02(){
        //User user=sqlSession.selectOne("user.getUserByName","%sp%");
        User user=sqlSession.selectOne("user.getUserByName","sp");
        System.out.println(user.getUsername());

        sqlSession.close();
    }

    @Test
    /**
     * 添加测试
     */
    public void test03(){
        User user=new User();
        user.setUsername("Julia");
        user.setGender("woman");
        user.setPhone("123456");
        user.setAddress("space");

        sqlSession.insert("user.insertUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test04(){
        User user=new User();
        user.setUsername("A2");
        user.setGender("woman");
        user.setPhone("Pod 042");
        user.setAddress("YoRHa");
        System.out.println(user.getId());
        sqlSession.insert("user.insertUserId",user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    /**
     *修改测试
     */
    public void test05(){
        User user=new User();
        user.setId(2);
        user.setAddress("space");

        sqlSession.update("user.updateUser",user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test06(){
        sqlSession.delete("user.deleteUserById",3);
        sqlSession.commit();
        sqlSession.close();
    }
}
