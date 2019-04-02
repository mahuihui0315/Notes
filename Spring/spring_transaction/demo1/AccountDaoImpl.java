package com.spring.transaction.demo1;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

    @Override
    public void out(String from, int money) {
        this.getJdbcTemplate().update("update account set money=money-? where name =?",money,from);
    }

    @Override
    public void in(String to, int money) {
        this.getJdbcTemplate().update("update account set money=money+? where name =?",money,to);
    }
}
