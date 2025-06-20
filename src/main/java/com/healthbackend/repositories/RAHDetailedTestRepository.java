package com.healthbackend.repositories;

import com.healthbackend.entities.RAHDetailedTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RAHDetailedTestRepository extends JpaRepository<RAHDetailedTest, Long> {
    
 

}
