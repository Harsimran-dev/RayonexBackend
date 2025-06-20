package com.healthbackend.repositories;

import com.healthbackend.entities.TreatmentPlan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {

    List<TreatmentPlan> findByClientId(Long clientId);
    // You can add custom query methods here if needed
}
