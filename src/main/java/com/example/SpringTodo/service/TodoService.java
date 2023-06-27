package com.example.SpringTodo.service;

import com.example.SpringTodo.entity.Image;
import com.example.SpringTodo.entity.Todo;
import com.example.SpringTodo.repository.impl.ImageRepository;
import com.example.SpringTodo.repository.impl.TodoRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Service
public class TodoService {

    @Autowired
    private TodoRepository _todoRepository;

    @Autowired
    private ImageRepository _imageRepository;

    @Autowired
    private UploadService uploadService;


    private Session _session;

    public Todo createTodo(String title, String description, Date dueDate, int priority) throws Exception {
        if(title == null || description == null) {
            throw new Exception("Remplir la totalité des champs");
        }
        Todo todo = new Todo(title, description, dueDate, priority);
        if(_todoRepository.create(todo)) {
            return todo;
        }
        return null;
    }

    public Todo createTodo(String title, String description, Date dueDate, int priority, List<MultipartFile> images) throws Exception {
        if(title == null || description == null || dueDate == null) {

            throw new Exception("Remplir la totalité des champs");
        }

        Todo todo = new Todo(title, description, dueDate, priority);

            if(_todoRepository.create(todo)) {
                for(MultipartFile img : images) {
                    Image image = new Image();
                    image.setUrl(uploadService.store(img));
                    image.setTodo(todo);
                    _imageRepository.create(image);
                    }

                    //todo.getImages().add(image);
                }
                return todo;
            }



    public Todo updateTodo(int id, String title, String description, Date dueDate, int priority) throws Exception {
        Todo todo = _todoRepository.findById(id);
        if(todo != null) {
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setDueDate(dueDate);
            todo.setPriority(priority);
            _todoRepository.update(todo);
            return todo;
        }
        throw new Exception("Aucune todo avec cet id");
    }

    public boolean deleteTodo(int id) throws Exception {
        Todo todo = _todoRepository.findById(id);
        if(todo != null) {
            _todoRepository.delete(todo);
            return true;
        }
        throw new Exception("Aucune todo avec cet id");
    }
    public List<Todo> getByStatus(boolean status) {
        return _todoRepository.findByStatus(status);
    }

    public Todo getTodoById(int id) {
        return _todoRepository.findById(id);
    }


    public List<Todo> findAll() {
        Query<Todo> todoQuery = _session.createQuery("from Todo");
        return todoQuery.list();    }
}
