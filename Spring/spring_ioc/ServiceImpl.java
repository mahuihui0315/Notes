package com.springIOC.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "serviceImpl")
public class ServiceImpl implements Service {
    @Autowired
    @Qualifier(value = "daoImpl")
    private Dao dao;
    @Override
    public void start() {
        System.out.println("ServiceImpl start");
        dao.start();
    }
}
