package com.healthbackend.controllers;

import com.healthbackend.entities.TreatmentPlanItem;
import com.healthbackend.repositories.TreatmentPlanRepository;
import com.healthbackend.services.TreatmentPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treatment-plan-items")
public class TreatmentPlanItemController {

    @Autowired
    private TreatmentPlanItemService itemService;
    @Autowired
private TreatmentPlanRepository planRepository;


    // Get all items
    @GetMapping
    public List<TreatmentPlanItem> getAllItems() {
        return itemService.getAllItems();
    }

    // Get item by ID
    @GetMapping("/{id}")
    public ResponseEntity<TreatmentPlanItem> getItemById(@PathVariable Long id) {
        Optional<TreatmentPlanItem> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadItem(
            @RequestParam("treatmentType") String treatmentType,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("completed") boolean completed,
            @RequestParam("close") boolean close,
            @RequestParam("treatmentPlanId") Long treatmentPlanId,
            @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile
    ) {
        try {
            TreatmentPlanItem item = new TreatmentPlanItem();
            item.setTreatmentType(treatmentType);
            item.setDate(date);
            item.setTime(time);
            item.setCompleted(completed);
            item.setClose(close);
            if (pdfFile != null && !pdfFile.isEmpty()) {
                item.setPdfResults(pdfFile.getBytes());
            }
    
            item.setTreatmentPlan(
                planRepository.findById(treatmentPlanId)
                    .orElseThrow(() -> new RuntimeException("Treatment plan not found"))
            );
    
            return ResponseEntity.ok(itemService.saveItem(item));
        } catch (Exception e) {
            e.printStackTrace(); // Logs full stack trace to console
            return ResponseEntity.status(500).body("Internal error: " + e.getMessage());
        }
    }
    


    // Get items by treatment plan ID
    @GetMapping("/plan/{planId}")
    public List<TreatmentPlanItem> getItemsByTreatmentPlan(@PathVariable Long planId) {
        return itemService.getItemsByTreatmentPlanId(planId);
    }

    // Save single item
    @PostMapping
    public TreatmentPlanItem saveItem(@RequestBody TreatmentPlanItem item) {
        return itemService.saveItem(item);
    }

    // Save multiple items
    @PostMapping("/bulk")
    public List<TreatmentPlanItem> saveMultipleItems(@RequestBody List<TreatmentPlanItem> items) {
        return itemService.saveItems(items);
    }

    // Delete item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
public ResponseEntity<?> updateItem(
        @PathVariable Long id,
        @RequestParam("treatmentType") String treatmentType,
        @RequestParam("date") String date,
        @RequestParam("time") String time,
        @RequestParam("completed") boolean completed,
        @RequestParam("close") boolean close,
        @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile
) {
    try {
        Optional<TreatmentPlanItem> optionalItem = itemService.getItemById(id);
        if (optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TreatmentPlanItem item = optionalItem.get();
        item.setTreatmentType(treatmentType); // Enum handling skipped as you're not using enum
        item.setDate(date);
        item.setTime(time);
        item.setCompleted(completed);
        item.setClose(close);

        if (pdfFile != null && !pdfFile.isEmpty()) {
            item.setPdfResults(pdfFile.getBytes());
        }

        TreatmentPlanItem updated = itemService.saveItem(item);
        return ResponseEntity.ok(updated);

    } catch (IOException e) {
        return ResponseEntity.status(500).body("Failed to process file: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Internal error: " + e.getMessage());
    }
}

}
