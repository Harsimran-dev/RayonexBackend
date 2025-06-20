package com.healthbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.healthbackend.entities.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByFirstNameAndSurname(String firstName, String surname);
    List<Client> findByMobile(String mobile);
}

