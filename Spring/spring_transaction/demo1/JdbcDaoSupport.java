package com.spring.transaction.demo1;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
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
