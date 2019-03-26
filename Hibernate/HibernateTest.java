package com.mhh.hibernate.demo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

public class HibernateTest {
    Configuration configuration=null;
    SessionFactory sessionFactory=null;
    Session session=null;
    Transaction transaction=null;

    @Before
    public void init(){
        configuration=new Configuration().configure("/hibernate.cfg.xml");
        sessionFactory=configuration.buildSessionFactory();
        session=sessionFactory.openSession();
        transaction=session.beginTransaction();
    }

    @Test
    public void insertTest(){
        CstCustomerEntity customer=new CstCustomerEntity();
        customer.setCustName("spike");
        customer.setCustIndustry("space cowboy");
        session.save(customer);
        transaction.commit();
    }

    @Test
    public void saveTest(){
        CstCustomerEntity customer=new CstCustomerEntity();
        customer.setCustName("Julia");
        Serializable id=session.save(customer);
        System.out.println(id);
        transaction.commit();
    }

    @Test
    public void getTest(){
        CstCustomerEntity customer=session.get(CstCustomerEntity.class,2l);
        System.out.println(customer.getCustName());
        transaction.commit();;
        session.close();
    }

    @Test
    public void updateTest(){
        CstCustomerEntity customer=session.get(CstCustomerEntity.class,1l);
        customer.setCustName("Jet");
        session.update(customer);
        transaction.commit();
        session.close();
    }

    @Test
    public void deleteTest(){
        CstCustomerEntity customer=session.get(CstCustomerEntity.class,2l);
        session.delete(customer);
        transaction.commit();
        session.close();
    }

    @Test
    public void findAll(){
        //接收HQL语句：Hibernate Query Language
        Query query=session.createQuery("from CstCustomerEntity ");
        List<CstCustomerEntity> list=query.list();
        for (CstCustomerEntity l:list) {
            System.out.println(l.getCustName());
        }
    }
}
