package com.healthbackend.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor  // Lombok will generate a no-argument constructor
@AllArgsConstructor // Lombok will generate a constructor with all arguments (including `id`)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String firstName;
    private String surname;
    private String dateOfBirth;
    private String gender;
    private String address;
    private String postCode;
    private String email;
    private String telephone;
    private String mobile;

    @OneToMany
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "client")
    private List<Treatment> treatments;

    @OneToMany(mappedBy = "client")
    private List<RAHDetailedTest> rahDetailedTests;

    @OneToMany
    private List<TreatmentPlan> treatmentPlans;

    // Constructor with Long id (optional, if needed)
    public Client(Long id) {
        this.id = id;
    }
}
