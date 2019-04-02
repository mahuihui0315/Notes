package com.spring.transaction.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DemoTest {

    @Resource(name = "accountService")
    private AccountService accountService;

    @Test
    /**
     * Spring的JDBC测试
     */
    public void test01(){
        accountService.transfer("Spike","Jet",1000);
    }
}
