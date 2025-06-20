package com.healthbackend.dtos;

import com.healthbackend.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt;
    private Role userRole;
    private Long userId;
    private String email;

   
    private boolean mfaEnabled;
}