package com.example.SpringTodo.repository.impl;

import com.example.SpringTodo.entity.UserTodo;
import com.example.SpringTodo.repository.BaseRepository;
import com.example.SpringTodo.util.ServiceHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserTodoRepository implements BaseRepository<UserTodo> {

    private Session _session;

    private ServiceHibernate _serviceHibernate;

    public UserTodoRepository(ServiceHibernate serviceHibernate) {
        _serviceHibernate = serviceHibernate;
        _session = _serviceHibernate.getSession();
    }

    @Override
    public boolean create(UserTodo element) {
        _session.beginTransaction();
        _session.persist(element);
        _session.getTransaction().commit();
        return element.getId() > 0;
    }

    @Override
    public boolean update(UserTodo element) {
        return false;
    }

    @Override
    public boolean delete(UserTodo element) {
        return false;
    }

    @Override
    public UserTodo findById(int id) {
        return (UserTodo)_session.get(UserTodo.class, id);
    }

    @Override
    public List<UserTodo> findAll(UserTodo element) {
        Query<UserTodo> todoQuery = _session.createQuery("from Todo");
        return todoQuery.list();
    }

    public UserTodo findByName(String name) {
        Query<UserTodo> query = _session.createQuery("from UserTodo where name=:name", UserTodo.class);
        query.setParameter("name", name);
        return query.uniqueResult();
    }
}
