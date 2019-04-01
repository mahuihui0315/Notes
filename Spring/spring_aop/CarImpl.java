package com.spring.aop.AspectJ_demo;

import org.springframework.stereotype.Component;

//@Component(value = "car")
public class CarImpl implements  Car {
    @Override
    public void start() {
        System.out.println("启动汽车");
    }

    @Override
    public void stop() {
        System.out.println("停止汽车");
    }
}
