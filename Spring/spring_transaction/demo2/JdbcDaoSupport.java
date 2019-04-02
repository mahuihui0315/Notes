package com.spring.transaction.aop_demo2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component(value = "jdbcDaoSupport")
public class JdbcDaoSupport {
    @Resource(name = "template")
    private JdbcTemplate jdbcTemplate;
    public JdbcTemplate getJdbcTemplate(){
        return jdbcTemplate;
    }
}
