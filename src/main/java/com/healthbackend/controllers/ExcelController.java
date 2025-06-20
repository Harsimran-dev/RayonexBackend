package com.healthbackend.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthbackend.entities.ExcelRecord;
import com.healthbackend.services.ExcelService;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
    
    private final ExcelService excelService;

    @Autowired
    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        logger.info("Received request to upload Excel file. File name: {}", file.getOriginalFilename());

        try {
            // Save the data to the database using the correct method
            excelService.saveExcelDataToDatabase(file);
            logger.info("File uploaded and data saved to the database.");

            return ResponseEntity.ok("File uploaded and data saved to the database.");

        } catch (IOException e) {
            logger.error("Failed to process the Excel file: {}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to process the Excel file: " + e.getMessage());
        }
    }

    @GetMapping("/findByRahId")
    public ResponseEntity<ExcelRecord> getExcelRecordByRahId(@RequestParam("rahId") String rahId) {
        Optional<ExcelRecord> record = excelService.getExcelRecordByRahId(rahId);

        if (record.isPresent()) {
            return ResponseEntity.ok(record.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/searchByRahId")
    public ResponseEntity<List<ExcelRecord>> searchExcelRecords(@RequestParam("query") String query) {
        List<ExcelRecord> records = excelService.searchExcelRecordsByRahId(query);
        return ResponseEntity.ok(records);
    }
}
