package com.healthbackend.repositories;

import java.util.Optional;

import com.healthbackend.entities.PensionUser;
import com.healthbackend.enums.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PensionUserRepository extends JpaRepository<PensionUser, Long> {
    Optional<PensionUser> findByEmail(String email);
    Optional<PensionUser> findByPhoneNumber(String phoneNumber);
    PensionUser findByRole(Role role);
}
