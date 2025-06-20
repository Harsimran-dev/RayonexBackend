package com.healthbackend.repositories;

import com.healthbackend.entities.TreatmentPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreatmentPlanItemRepository extends JpaRepository<TreatmentPlanItem, Long> {
    List<TreatmentPlanItem> findByTreatmentPlanId(Long treatmentPlanId);
}
