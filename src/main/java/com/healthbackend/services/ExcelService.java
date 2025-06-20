package com.healthbackend.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healthbackend.entities.ExcelRecord;
import com.healthbackend.repositories.ExcelRecordRepository;

@Service
public class ExcelService {

    private final ExcelRecordRepository excelRecordRepository;

    @Autowired
    public ExcelService(ExcelRecordRepository excelRecordRepository) {
        this.excelRecordRepository = excelRecordRepository;
    }

    public void saveExcelDataToDatabase(MultipartFile file) throws IOException {
        List<ExcelRecord> records = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            Iterator<Row> rowIterator = sheet.iterator();

            // Skip the header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                ExcelRecord record = new ExcelRecord();

                record.setRahId(getCellValue(row, 0));
                record.setDetails(getCellValue(row, 1));
                record.setCategory(getCellValue(row, 2));
                record.setDescription(getCellValue(row, 3));
                record.setImage(getCellValue(row, 4));
                record.setRecommendation(getCellValue(row, 5));
                record.setSeventiesId(getCellValue(row, 10));
                record.setCorrespondingPathogens(getCellValue(row, 11));  // from "Corresponding pathogens"
                String compactId = getCellValue(row, 15);
                if (compactId != null && compactId.length() > 255) {
                    compactId = compactId.substring(0, 255);
                }
                record.setCompactId(compactId);
                            // 🔁 updated from column 12 to 15
                record.setProgramDetails(getCellValue(row, 16));          // was 13, now correctly 16
                
                       // Column 12 (Excel index 11) - Program Details

                records.add(record);
            }

            workbook.close();
        }

        // Save all records in the database
        excelRecordRepository.saveAll(records);
    }

    public Optional<ExcelRecord> getExcelRecordByRahId(String rahId) {
        return excelRecordRepository.findByRahId(rahId);
    }

    public List<ExcelRecord> searchExcelRecordsByRahId(String rahIdPrefix) {
        return excelRecordRepository.findByRahIdStartingWith(rahIdPrefix);
    }

    // Utility method to get cell value safely
    private String getCellValue(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        return (cell != null) ? cell.toString().trim() : "";
    }
}
