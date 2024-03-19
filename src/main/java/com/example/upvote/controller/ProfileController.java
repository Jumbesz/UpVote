package com.example.upvote.controller;

import com.example.upvote.dto.incoming.IdeaCreationData;
import com.example.upvote.model.Idea;
import com.example.upvote.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create-idea")
    public ResponseEntity<Idea> createIdea(@RequestBody IdeaCreationData ideaCreationData) {
        return new ResponseEntity<>(profileService.createIdea(ideaCreationData), HttpStatus.CREATED);
    }
    
}
