package com.example.SpringTodo.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



public interface UploadService {
    String store(MultipartFile file) throws IOException;
}
