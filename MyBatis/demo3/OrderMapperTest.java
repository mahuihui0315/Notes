package com.mybatis.demo3;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OrderMapperTest {
    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder=null;
    private InputStream stream=null;
    private SqlSessionFactory sqlSessionFactory=null;
    private SqlSession sqlSession=null;

    @Before
    public void init() throws IOException {
        sqlSessionFactoryBuilder=new SqlSessionFactoryBuilder();
        stream= Resources.getResourceAsStream("SqlMapConfig.xml");
        sqlSessionFactory=sqlSessionFactoryBuilder.build(stream);
        sqlSession=sqlSessionFactory.openSession();
    }

    @Test
    /**
     * getOrderList测试
     */
    public void test01(){
        List<Order> orders=sqlSession.selectList("getOrderList");
        for (Order order:orders){
            System.out.println(order.toString());
        }
    }

    @Test
    /**
     * getOrderListMap测试
     */
    public void test02(){
        OrderMapper orderMapper=sqlSession.getMapper(OrderMapper.class);
        List<Order> orders=orderMapper.getOrderListMap();
        for (Order order:orders){
            System.out.println(order.toString());
        }
    }

    @Test
    /**
     * getOrderUserMap测试
     */
    public void test03(){
        OrderMapper orderMapper=sqlSession.getMapper(OrderMapper.class);
        List<Order> list=orderMapper.getOrderUserMap();
        for (Order order:list) {
            System.out.println(order.toString());
        }
    }
}
