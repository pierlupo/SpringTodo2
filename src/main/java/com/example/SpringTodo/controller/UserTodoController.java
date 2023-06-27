package com.example.SpringTodo.controller;

import com.example.SpringTodo.service.UserTodoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
@Controller
@RequestMapping("/user")
public class UserTodoController {


    @Autowired
    private UserTodoService _userTodoService;

    @Autowired
    private HttpServletResponse _response;



    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @PostMapping("/submit")
    public ModelAndView submitLogin(@RequestParam String name, @RequestParam String password) throws IOException {
        if(_userTodoService.login(name, password)) {
            _response.sendRedirect("/todo");
        }
        ModelAndView mv = new ModelAndView("login");
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView getRegistered() {
        ModelAndView mv = new ModelAndView("register");
        return mv;
    }
    @PostMapping("sign-in-submit")
    public ModelAndView submitSignIn(@RequestParam String name, @RequestParam String email, @RequestParam String password) throws IOException {
        ModelAndView mv = new ModelAndView("register");
        if(_userTodoService.signIn(name, email, password)) {
            _response.sendRedirect("/user/login");
        }
        mv.addObject("name", name);
        mv.addObject("email", email);
        return mv;
    }
//    @GetMapping("protected")
//    public String protectedPage() throws IOException {
//        if(!_userTodoService.isLogged()){
//            _response.sendRedirect("login");
//        }
//        return "redirect:/todo/home";
//    }
}
