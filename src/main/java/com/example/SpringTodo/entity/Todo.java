package com.example.SpringTodo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name= "todos")
@Builder
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String description;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private int priority;

    private boolean status;

    @OneToMany(mappedBy = "todo", fetch = FetchType.EAGER)
    private List<Image> images;

    public Todo() {

    }

    public Todo(String title, String description, Date dueDate, int priority) {
        this();
        this.setTitle(title);
        this.setDescription(description);
        this.setDueDate(dueDate);
        this.setPriority(priority);
        this.setStatus(false);

    }

    public Todo(String title, String description) {
        this();
        this.setTitle(title);
        this.setDescription(description);
        this.setStatus(false);
        this.setDueDate(new Date());
    }

}
