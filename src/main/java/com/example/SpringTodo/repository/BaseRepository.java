package com.example.SpringTodo.repository;

import java.util.List;

public interface BaseRepository<T> {
    public boolean create(T element);
    public boolean update(T element);
    public boolean delete(T element);

    public T findById(int id);
    List<T> findAll(T element);
}
