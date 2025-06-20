package com.healthbackend.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthbackend.entities.PensionUser;
import com.healthbackend.repositories.PensionUserRepository;
import com.healthbackend.services.AuthService;

@RestController
@RequestMapping("/api/v1/admin/")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AuthService authService;

       private final PensionUserRepository userRepository;


    @Autowired
    public AdminController(AuthService authService, PensionUserRepository userRepository ) {
        this.authService = authService;

        this.userRepository=userRepository;
       
    }

    @GetMapping("/users")
    public ResponseEntity<List<PensionUser>> getAllUsers() {
        List<PensionUser> users = authService.listAllUsers();
        return ResponseEntity.ok(users);
    }

    



    
  


}
