package com.healthbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthbackend.entities.Appointment;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClientId(Long clientId);  // Add this method
}
