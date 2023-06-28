package com.example.SpringTodo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    @ManyToOne(cascade = CascadeType.ALL)
    private Todo todo;

}