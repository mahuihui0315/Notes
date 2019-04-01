package com.spring.aop.AspectJ_demo;

import org.springframework.stereotype.Component;

//@Component(value ="myAspect")
public class MyAspect {
    public void checkCar(){
        System.out.println("检查汽车状况");
    }

    public void fixCar(){
        System.out.println("修理汽车");
    }
}
