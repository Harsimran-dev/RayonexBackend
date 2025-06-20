package com.healthbackend.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.healthbackend.entities.ExcelRecord;

public interface ExcelRecordRepository extends JpaRepository<ExcelRecord, Long> {
    Optional<ExcelRecord> findByRahId(String rahId);

    
    @Query("SELECT e FROM ExcelRecord e WHERE e.rahId LIKE :rahId%")
    List<ExcelRecord> findByRahIdStartingWith(@Param("rahId") String rahId);
}
