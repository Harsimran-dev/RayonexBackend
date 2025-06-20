package com.healthbackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "excel_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rah_id")
    private String rahId;

    @Column(name = "details", columnDefinition = "TEXT") 
    private String details;

    @Column(name = "category")
    private String category;

    @Column(name = "description", columnDefinition = "TEXT") 
    private String description;

    @Column(name = "compact_id")
    private String compactId;
    @Column(name = "image", columnDefinition = "TEXT")
private String image;

@Column(name = "recommendation", columnDefinition = "TEXT")
private String recommendation;


    @Column(name = "program_details", columnDefinition = "TEXT") 
    private String programDetails;

    @Column(name = "seventies_id", length = 255) // Adjust the length as needed
    private String seventiesId;
    

    @Column(name = "corresponding_pathogens", columnDefinition = "TEXT") 
    
    private String correspondingPathogens;
}
