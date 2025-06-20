package com.healthbackend.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.healthbackend.services.FileService;

import java.util.List;
import java.util.Optional;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file and get the file path
            String filePath = fileService.saveFile(file);

            return "File uploaded successfully. File path: " + filePath;
        } catch (IOException e) {
            return "File upload failed: " + e.getMessage();
        }
    }
}
