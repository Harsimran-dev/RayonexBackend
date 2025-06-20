package com.healthbackend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Save the file and return the file path
    public String saveFile(MultipartFile file) throws IOException {
        // Create a folder if it doesn't exist
        File folder = new File(uploadDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Generate a file name and store it in the folder
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + File.separator + fileName);

        // Save the file
        Files.write(filePath, file.getBytes());

        return "/uploads/docs/" + fileName; // This is the relative file path to store in the database
    }
}
