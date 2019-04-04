package com.mybatis.demo2;

import com.mybatis.demo1.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class DaoTest {
    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder=null;
    private InputStream stream=null;
    private SqlSessionFactory sqlSessionFactory=null;
    private SqlSession sqlSession=null;

    @Before
    public void init() throws IOException {
        //创建SqlSessionFactoryBuilder对象
        sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        //创建输入流引入配置文件
        stream= Resources.getResourceAsStream("SqlMapConfig.xml");
        //加载配置文件，创建SqlSessionFactory对象
        sqlSessionFactory=sqlSessionFactoryBuilder.build(stream);
        //创建SqlSession对象
        sqlSession=sqlSessionFactory.openSession();
    }

    @Test
    public void test01(){
        User user=sqlSession.selectOne("getUserById",1);
        System.out.println(user.getUsername());
    }
}
