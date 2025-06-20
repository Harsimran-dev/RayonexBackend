package com.healthbackend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TreatmentPlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String treatmentType;

    private String date;

    private String time;

    private Boolean completed;

    private Boolean close;
    private Long clientId;

    @Lob
    @Column(name = "pdf_results", columnDefinition = "LONGBLOB")
    private byte[] pdfResults;
    

    @ManyToOne
    @JoinColumn(name = "treatment_plan_id")
    @JsonBackReference
    private TreatmentPlan treatmentPlan;
}
