package com.example.SpringTodo.service;

import com.example.SpringTodo.entity.UserTodo;
import com.example.SpringTodo.repository.impl.UserTodoRepository;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTodoService {

    @Autowired
    private HttpSession _httpSession;
    @Autowired
    private UserTodoRepository _userTodoRepository;

    private Session session;


    public boolean signIn(String name, String email, String password) {
        if(!searchByName(name))  {
            //On peut appliquer des vérifications sur les champs firstName,...
            UserTodo user = UserTodo.builder()
                    .name(name)
                    //Hash du password
                    .password(password)
                    .email(email)
                    .build();
            if(_userTodoRepository.create(user)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean login(String name, String password) {
        UserTodo userTodo = _userTodoRepository.findByName(name);
        if(userTodo != null && userTodo.getPassword().equals(password)) {
            _httpSession.setAttribute("isLogged", "OK");
            return true;
        }
        return false;
    }

    public boolean isLogged() {
        try {
            String attrIsLogged = _httpSession.getAttribute("isLogged").toString();
            return attrIsLogged.equals("OK");
        }catch (Exception ex) {
            return false;
        }
    }

    public boolean searchByName(String name) {
        return _userTodoRepository.findByName(name) != null;
    }


}
