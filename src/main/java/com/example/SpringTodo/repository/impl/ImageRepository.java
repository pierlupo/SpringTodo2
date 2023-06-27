package com.example.SpringTodo.repository.impl;

import com.example.SpringTodo.entity.Image;
import com.example.SpringTodo.repository.BaseRepository;
import com.example.SpringTodo.util.ServiceHibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImageRepository implements BaseRepository<Image> {

    private Session _session;
    private ServiceHibernate _serviceHibernate;

    public ImageRepository(ServiceHibernate serviceHibernate) {
        _serviceHibernate = serviceHibernate;
        _session = _serviceHibernate.getSession();
    }
    @Override
    public boolean create(Image element) {
        _session.beginTransaction();
        _session.persist(element);
        _session.getTransaction().commit();
        return element.getId() > 0;
    }

    @Override
    public boolean update(Image element) {
        return false;
    }

    @Override
    public boolean delete(Image element) {
        return false;
    }

    @Override
    public Image findById(int id) {
        return null;
    }

    @Override
    public List<Image> findAll(Image element) {
        return null;
    }
}
