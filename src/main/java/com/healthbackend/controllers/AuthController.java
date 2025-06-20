package com.healthbackend.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthbackend.Util.JwtUtil;
import com.healthbackend.dtos.AuthRequest;
import com.healthbackend.dtos.AuthResponse;
import com.healthbackend.dtos.PensionUserDto;
import com.healthbackend.dtos.VerfRequest;
import com.healthbackend.entities.PensionUser;
import com.healthbackend.repositories.PensionUserRepository;
import com.healthbackend.services.AuthService;
import com.healthbackend.services.AuthServiceImpl;
import com.healthbackend.services.jwt.UserDetailsServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth/pension")
public class AuthController {
    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

        private final AuthService authService;
      
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    public final JwtUtil jwtUtil;
    private final PensionUserRepository userRepository;

    public AuthController(AuthService authService, 
    AuthenticationManager authenticationManager, 
    UserDetailsServiceImpl userDetailsServiceImpl, 
    JwtUtil jwtUtil, 
    PensionUserRepository userRepository) {
this.authService = authService;
this.authenticationManager = authenticationManager;

this.userDetailsServiceImpl = userDetailsServiceImpl;
this.jwtUtil = jwtUtil;
this.userRepository = userRepository;

}

@PostMapping("/signup")
public ResponseEntity<?> signupuser(@RequestBody PensionUser user) {
    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
    }

    if (userRepository.findByPhoneNumber(user.getPhoneNumber()).isPresent()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists");
    }

    PensionUserDto createdUser = authService.createUser(user);
    if (createdUser == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
}


@PostMapping("/login")
public AuthResponse createAuthenticationToken(
        @RequestBody AuthRequest authenticationRequest,
        HttpServletResponse response) throws IOException {
    
    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
            )
        );
    } catch (BadCredentialsException e) {
        throw new BadCredentialsException("Incorrect Username or password");
    } catch (DisabledException disabledException) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not active");
        return null;
    }
    
    final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticationRequest.getEmail());
    
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());
    
    Optional<PensionUser> optionalUser = userRepository.findByEmail(userDetails.getUsername());
    
    AuthResponse authenticationResponse = new AuthResponse();

    optionalUser.ifPresent(user -> {
    
       
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setJwt(jwt);
        authenticationResponse.setUserRole(user.getRole());
        authenticationResponse.setUserId(user.getId());
        
    });

    return authenticationResponse;
}




@PostMapping("/verify")
public ResponseEntity<?> verifyCode(@RequestBody VerfRequest verificationRequest) {
    try {
        AuthResponse authenticationResponse = authService.verifyCode(verificationRequest);
        return ResponseEntity.ok(authenticationResponse);
    } catch (BadCredentialsException e) {
        return ResponseEntity.badRequest().body("Invalid OTP code");
    }
}
@GetMapping("/users")
public ResponseEntity<List<PensionUser>> getAllUsers() {
    List<PensionUser> users = authService.listAllUsers();
    return ResponseEntity.ok(users);
}


 



@GetMapping("/{userId}")
public ResponseEntity<?> getUserById(@PathVariable Long userId) {
    try {
        PensionUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return ResponseEntity.ok(user);
    } catch (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user");
    }
}
 


  
    
}