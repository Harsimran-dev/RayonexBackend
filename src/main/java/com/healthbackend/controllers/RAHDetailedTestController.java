package com.healthbackend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.healthbackend.entities.Client;
import com.healthbackend.entities.RAHDetailedTest;
import com.healthbackend.repositories.RAHDetailedTestRepository;
import com.healthbackend.services.FileService;

@RestController
@RequestMapping("/api/rah-details")
public class RAHDetailedTestController {

    @Autowired
    private RAHDetailedTestRepository rahDetailedTestRepository;
    @Autowired
    private FileService fileService;

    @PostMapping("/add")
    public String addRAHDetailedTest(@RequestParam("file") MultipartFile file, @RequestParam("clientId") Long clientId) {
        try {
            // Save the file and get the file path
            String filePath = fileService.saveFile(file);

            // Create and save the RAHDetailedTest entity
            RAHDetailedTest test = new RAHDetailedTest();
            test.setClient(new Client(clientId));  // Assuming you have a client entity
            test.setFilePath(filePath); // Set the file path
            rahDetailedTestRepository.save(test);

            return "RAH Detailed Test added successfully!";
        } catch (IOException e) {
            return "Error uploading file: " + e.getMessage();
        }
    }
}
