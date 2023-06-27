package com.example.SpringTodo.repository.impl;

import com.example.SpringTodo.entity.Todo;
import com.example.SpringTodo.repository.BaseRepository;
import com.example.SpringTodo.util.ServiceHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TodoRepository implements BaseRepository<Todo> {

    private Session _session;

    private ServiceHibernate _serviceHibernate;

    public TodoRepository(ServiceHibernate serviceHibernate) {
        _serviceHibernate = serviceHibernate;
        _session = _serviceHibernate.getSession();
    }

    @Override
    public boolean create(Todo element) {
        _session.beginTransaction();
        _session.persist(element);
        _session.getTransaction().commit();
        return element.getId() > 0;
    }

    @Override
    public boolean update(Todo element) {
        _session.beginTransaction();
        _session.update(element);
        _session.getTransaction().commit();
        return true;
    }

    @Override
    public boolean delete(Todo element) {
        _session.beginTransaction();
        _session.delete(element);
        _session.getTransaction().commit();
        return true;
    }

    @Override
    public Todo findById(int id) {
        return (Todo)_session.get(Todo.class, id);
    }

    @Override
    public List<Todo> findAll(Todo element) {
        Query todoQuery = _session.createQuery("from Todo");
        return todoQuery.list();
    }

    public List<Todo> findByStatus(boolean status) {
        Query<Todo> todoQuery = _session.createQuery("from Todo where status = :status", Todo.class);
        todoQuery.setParameter("status", status);
        return todoQuery.list();
    }
}
