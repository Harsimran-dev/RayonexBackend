package com.healthbackend.services;

import java.util.List;

import com.healthbackend.dtos.AuthResponse;
import com.healthbackend.dtos.PensionUserDto;
import com.healthbackend.dtos.VerfRequest;
import com.healthbackend.entities.PensionUser;

public interface AuthService {

    PensionUserDto createUser(PensionUser user);
    AuthResponse verifyCode(VerfRequest verificationRequest);
    void deleteUserById(Long userId);
    List<PensionUser> listAllUsers();



 
}