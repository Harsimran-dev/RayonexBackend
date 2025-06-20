package com.healthbackend.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TreatmentPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    private boolean closeRecord;

    @Lob
    @Column(columnDefinition = "LONGBLOB")  // Use BYTEA if you're on PostgreSQL
    private byte[] rayostanUhedIst;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] rayoscin40Report;

    private String rahDescription;

    private Long clientId;

    @OneToMany(mappedBy = "treatmentPlan", cascade = CascadeType.ALL)
@JsonManagedReference
private List<TreatmentPlanItem> treatmentPlanItems;

}
