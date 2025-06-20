package com.healthbackend.controllers;

import com.healthbackend.entities.TreatmentPlan;
import com.healthbackend.services.TreatmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/treatment-plans")
public class TreatmentPlanController {

    private final TreatmentPlanService treatmentPlanService;

    @Autowired
    public TreatmentPlanController(TreatmentPlanService treatmentPlanService) {
        this.treatmentPlanService = treatmentPlanService;
    }

    // Upload Treatment Plan with PDFs
    @PostMapping("/upload")
    public ResponseEntity<?> uploadTreatmentPlan(
            @RequestParam("clientId") Long clientId,
            @RequestParam("rayostanUhedIst") MultipartFile rayostanUhedIstFile,
            @RequestParam("rayoscin40Report") MultipartFile rayoscin40ReportFile,
            @RequestParam("rahDescription") String rahDescription,
            @RequestParam("closeRecord") boolean closeRecord
    ) {
        try {
            TreatmentPlan savedPlan = treatmentPlanService.saveTreatmentPlan(
                    clientId,
                    rayostanUhedIstFile,
                    rayoscin40ReportFile,
                    rahDescription,
                    closeRecord
            );
            return ResponseEntity.ok(savedPlan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving treatment plan: " + e.getMessage());
        }
    }

    // Download rayoscin40Report PDF
    @GetMapping("/{id}/rayoscin40")
    public ResponseEntity<byte[]> downloadRayoscin40(@PathVariable Long id) {
        byte[] pdf = treatmentPlanService.getRayoscin40Pdf(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rayoscin40.pdf")
                .body(pdf);
    }

    // Download rayostanUhedIst PDF
    @GetMapping("/{id}/rayostan-uhed-ist")
    public ResponseEntity<byte[]> downloadRayostanUhedIst(@PathVariable Long id) {
        byte[] pdf = treatmentPlanService.getRayostanUhedIstPdf(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rayostan_uhed_ist.pdf")
                .body(pdf);
    }

    // Get Treatment Plans by clientId
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<TreatmentPlan>> getTreatmentPlansByClientId(@PathVariable Long clientId) {
        List<TreatmentPlan> plans = treatmentPlanService.getTreatmentPlansByClientId(clientId);
        return ResponseEntity.ok(plans);
    }

    // Get Treatment Plan by ID
@GetMapping("/{id}")
public ResponseEntity<TreatmentPlan> getTreatmentPlanById(@PathVariable Long id) {
    try {
        TreatmentPlan plan = treatmentPlanService.getTreatmentPlanById(id);
        return ResponseEntity.ok(plan);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
    }
}
@PutMapping("/{id}/close-record")
public ResponseEntity<TreatmentPlan> updateCloseRecord(
        @PathVariable Long id,
        @RequestParam boolean closeRecord) {
    try {
        TreatmentPlan updatedPlan = treatmentPlanService.updateCloseRecordStatus(id, closeRecord);
        return ResponseEntity.ok(updatedPlan);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

@PutMapping("/{id}/description")
public ResponseEntity<TreatmentPlan> updateRahDescription(
        @PathVariable Long id,
        @RequestBody String newDescription) {
    TreatmentPlan updated = treatmentPlanService.updateRahDescription(id, newDescription);
    return ResponseEntity.ok(updated);
}



}
