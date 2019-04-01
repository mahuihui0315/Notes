package com.springIOC.demo1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCTest {
    @Test
    /**
     * SpringIOC注解开发测试
     */
    public void test01(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        Dao dao=(Dao) applicationContext.getBean("daoImpl");
        dao.start();
    }

    @Test
    /**
     * IOC注解开发属性注入测试
     */
    public void test02(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        DaoImpl dao=(DaoImpl) applicationContext.getBean("daoImpl");
        System.out.println(dao.getName());
    }

    @Test
    /**
     * 对象属性注入测试
     * 1.Autowired
     * 2.Qualifier
     */
    public void test03(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        ServiceImpl service=(ServiceImpl)applicationContext.getBean("serviceImpl");
        service.start();
    }
}
