package com.springIOC.demo1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @Component(value = "daoImpl"相当于
 * <bean id="xxx" class="xxx"></bean>
 */
@Component(value = "daoImpl")
public class DaoImpl implements Dao {
    private String name;

    @Value("spike")
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void start() {
        System.out.println("DaoImpl start");
    }
}
