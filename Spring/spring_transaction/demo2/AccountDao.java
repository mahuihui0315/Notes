package com.spring.transaction.aop_demo2;

public interface AccountDao {
    public void out(String from, int money);
    public void in(String to, int money);
}
