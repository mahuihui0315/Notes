package com.spring.transaction.aop_demo1;

import com.spring.transaction.demo1.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext3.xml")
public class DemoTest {

    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    /**
     * Spring声明式事务管理测试
     * 2.注解方式
     */
    public void test01(){
        accountService.transfer("Spike","Jet",1000);
    }
}
