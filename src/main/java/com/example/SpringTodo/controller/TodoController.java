package com.example.SpringTodo.controller;

import com.example.SpringTodo.entity.Todo;
import com.example.SpringTodo.service.TodoService;
import com.example.SpringTodo.service.UserTodoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    private String location = "images";

    @Autowired
    private TodoService _todoService;

    @Autowired
    private HttpServletResponse _response;

    @Autowired
    private UserTodoService _userTodoService;


    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable Integer id) throws Exception {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        return _todoService.deleteTodo(id);
    }

    @GetMapping("/all")
    public List<Todo> getTodos() {
        List<Todo> todos = _todoService.getAllTodos();
        return todos;
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Integer id) {
        List<Todo> todos = _todoService.getAllTodos();
        Todo todo = new Todo();
        for (Todo t : todos) {
            if (t.getId() == id) {
                todo = t;
            }
        }
        return todo;
    }

//    @GetMapping("/all")
//    public Todo getAllTodos() {
//        List<Todo> todos = _todoService.findAll();
//        return (Todo) todos;
//    }

    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Integer id, Model model) throws IOException {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        Todo to = _todoService.getTodoById(id);
        System.out.println("to " + to);
        model.addAttribute("todo", to);
        return "formulaire";
    }


    @GetMapping("/search")
    public String searchTodoById(@RequestParam("todoId") Integer todoId, Model model) throws IOException {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        Todo todo = _todoService.getTodoById(todoId);
        model.addAttribute("todo", todo);
        return "tododetails";
    }

    @GetMapping("/formupload")
    public ModelAndView form() throws IOException {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        ModelAndView vm = new ModelAndView("form-upload");
        return vm;
    }

    @GetMapping("/form")
    public ModelAndView getForm() throws IOException {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        ModelAndView mv = new ModelAndView("formulaire");
        mv.addObject("todo", new Todo());
        return mv;
    }


//    @GetMapping("files")
//    @ResponseBody
//    public List<String> getFiles() throws IOException {
//        List<String> liste = new ArrayList<>();
//        Files.walk(Paths.get(this.location)).forEach(path -> {
//            liste.add(path.getFileName().toString());
//        });
//        return liste;
//    }


    @GetMapping("/update/{id}")
    public boolean updateStatus(@PathVariable Integer id) throws Exception {
        try {
            return _todoService.updateStatus(id);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @GetMapping("{status}")
    public List<Todo> get(@PathVariable boolean status) {

        return _todoService.getByStatus(status);
    }

    @GetMapping(value = {"","{status}"})
    public ModelAndView getTodos(@PathVariable(required = false) Boolean status) {
        ModelAndView mv = new ModelAndView("home");
        List<Todo> todos = new ArrayList<>();
        if(status == null) {
            todos.addAll(_todoService.getByStatus(false));
            todos.addAll(_todoService.getByStatus(true));
        }
        else {
            todos.addAll(_todoService.getByStatus(status));
        }
        mv.addObject("todos", todos);
        return mv;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView getDetail(@PathVariable Integer id) throws IOException {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        ModelAndView mv = new ModelAndView("tododetails");
        mv.addObject("todo", _todoService.getTodoById(id));
        mv.addObject("isLogged", _userTodoService.isLogged());
        return mv;
    }


    @PostMapping("/submitForm")
    public ModelAndView submitForm(@RequestParam String title, @RequestParam String description, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dueDate, @RequestParam int priority, @RequestParam("images") List<MultipartFile> images, HttpServletResponse response) throws IOException {
        if(!_userTodoService.isLogged()) {
            _response.sendRedirect("/user/login");
        }
        ModelAndView mv = new ModelAndView("formulaire");
        try {
           Todo todo = _todoService.createTodo(title, description, dueDate, priority, images);
            _response.sendRedirect("/todo");
        }catch (Exception ex) {
            mv.addObject("message", "erreur lors de l'ajout");
            mv.addObject("todo", new Todo());
        }

        return mv;
    }

//    @PostMapping("submitImage")
//    public String submitImage(@RequestParam("image") MultipartFile image) throws IOException {
//        if(!_userTodoService.isLogged()) {
//            _response.sendRedirect("/todo/login");
//        }
//        Path destinationFile = Paths.get(location).resolve(Paths.get(image.getOriginalFilename())).toAbsolutePath();
//        InputStream stream = image.getInputStream();
//        Files.copy(stream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
//        return "redirect:/todo/form-upload";
//    }
}
