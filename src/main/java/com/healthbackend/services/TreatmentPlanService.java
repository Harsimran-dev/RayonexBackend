package com.healthbackend.services;

import com.healthbackend.entities.TreatmentPlan;
import com.healthbackend.repositories.TreatmentPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TreatmentPlanService {

    private final TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    public TreatmentPlanService(TreatmentPlanRepository treatmentPlanRepository) {
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    // Save new Treatment Plan
    public TreatmentPlan saveTreatmentPlan(Long clientId,
                                           MultipartFile rayostanUhedIstFile,
                                           MultipartFile rayoscin40ReportFile,
                                           String rahDescription,
                                           boolean closeRecord) throws IOException {

        TreatmentPlan treatmentPlan = new TreatmentPlan();
        treatmentPlan.setClientId(clientId);
        treatmentPlan.setRayostanUhedIst(rayostanUhedIstFile.getBytes());
        treatmentPlan.setRayoscin40Report(rayoscin40ReportFile.getBytes());
        treatmentPlan.setRahDescription(rahDescription);
        treatmentPlan.setCloseRecord(closeRecord);
        treatmentPlan.setDate(LocalDate.now().toString());

        return treatmentPlanRepository.save(treatmentPlan);
    }

    // Get Treatment Plan by ID
    public TreatmentPlan getTreatmentPlanById(Long id) {
        return treatmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treatment Plan not found with ID: " + id));
    }

    // Get PDF for Rayoscin40 report
    public byte[] getRayoscin40Pdf(Long id) {
        return getTreatmentPlanById(id).getRayoscin40Report();
    }

    // Get PDF for Rayostan Uhed Ist report
    public byte[] getRayostanUhedIstPdf(Long id) {
        return getTreatmentPlanById(id).getRayostanUhedIst();
    }

    // Get all Treatment Plans by clientId
    public List<TreatmentPlan> getTreatmentPlansByClientId(Long clientId) {
        return treatmentPlanRepository.findByClientId(clientId);
    }

    public TreatmentPlan updateCloseRecordStatus(Long id, boolean closeRecord) {
        TreatmentPlan plan = treatmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treatment Plan not found with ID: " + id));
        plan.setCloseRecord(closeRecord);
        return treatmentPlanRepository.save(plan);
    }

    public TreatmentPlan updateRahDescription(Long id, String newDescription) {
        TreatmentPlan plan = treatmentPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Treatment Plan not found with ID: " + id));
        plan.setRahDescription(newDescription);
        return treatmentPlanRepository.save(plan);
    }
    
    
}
