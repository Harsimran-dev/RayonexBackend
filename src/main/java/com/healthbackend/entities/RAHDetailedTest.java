package com.healthbackend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class RAHDetailedTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // The field to store the path or URL of the file in the filesystem
    private String filePath;  // This will store the location of the document (e.g., "/uploads/docs/client123.pdf")
}
