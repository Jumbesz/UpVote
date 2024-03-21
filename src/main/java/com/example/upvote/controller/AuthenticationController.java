package com.example.upvote.controller;

import com.example.upvote.dto.incoming.LoginRequest;
import com.example.upvote.dto.incoming.RegisterRequest;
import com.example.upvote.dto.outgoing.LoginResponse;
import com.example.upvote.model.Profile;
import com.example.upvote.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final ProfileService profileService;

    @Autowired
    public AuthenticationController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public ResponseEntity<Profile> register(@RequestBody RegisterRequest profileCreationData) {
        Profile registeredProfile = profileService.register(profileCreationData);
        if (registeredProfile != null) {
            return new ResponseEntity<>(registeredProfile, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Profile profile = profileService.login(loginRequest.getUsername(), loginRequest.getPassword(), request);

        if (profile != null) {
            return new ResponseEntity<>(new LoginResponse(profile.getId(), profile.getUsername(), profile.getProfileRole().toString()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful!");
    }

}
