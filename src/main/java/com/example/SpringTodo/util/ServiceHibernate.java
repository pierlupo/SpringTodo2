package com.example.SpringTodo.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
public class ServiceHibernate {

    private Session _session;

    private ServiceHibernate(){
        try{
            _session = HibernateUtil.getSessionFactory().openSession();
        }catch(HibernateException e){
            throw new RuntimeException();
        }
    }

    public Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
