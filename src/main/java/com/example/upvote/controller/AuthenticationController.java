package com.example.upvote.controller;

import com.example.upvote.dto.incoming.LoginData;
import com.example.upvote.dto.incoming.RegisterData;
import com.example.upvote.model.Profile;
import com.example.upvote.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private ProfileService profileService;

    @Autowired
    public AuthenticationController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<Profile> register(@RequestBody RegisterData profileCreationData) {
        Profile registeredProfile = profileService.register(profileCreationData);
        if (registeredProfile != null) {
            return new ResponseEntity<>(registeredProfile, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginData loginData) {
        Profile profile = profileService.login(loginData.getUsername(), loginData.getPassword());

        if (profile != null) {
            return ResponseEntity.ok("Login successful!");
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful!");
    }

}
