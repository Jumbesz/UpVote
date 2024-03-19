package com.example.upvote.controller;

import com.example.upvote.model.Idea;
import com.example.upvote.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private IdeaService ideaService;

    @Autowired
    public AdminController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @PutMapping("{id}/approve")
    public ResponseEntity<String> approveIdea(@PathVariable Long id) {
        return new ResponseEntity<>(ideaService.approveIdea(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Idea>> getPendingIdeas() {
        if (ideaService.getPendingIdeas() != null) {
            return new ResponseEntity<>(ideaService.getPendingIdeas(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
