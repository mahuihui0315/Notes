package com.spring.aop.AspectJ_demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AspectJTest {

    @Resource(name="car")
    private Car car;

    @Test
    public void test01(){
        car.start();
        car.stop();
    }
}
