package com.example.upvote.controller;

import com.example.upvote.dto.outgoing.PendingIdeasResponse;
import com.example.upvote.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IdeaService ideaService;

    @Autowired
    public AdminController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @PutMapping("{id}/approve")
    public ResponseEntity<String> approveIdea(@PathVariable Long id) {
        return new ResponseEntity<>(ideaService.approveIdea(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}/disapprove")
    public ResponseEntity<String> disapproveIdea(@PathVariable Long id) {
        return new ResponseEntity<>(ideaService.disapproveIdea(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PendingIdeasResponse> getPendingIdeas() {
        if (ideaService.getPendingIdeas() != null) {
            return new ResponseEntity<>(ideaService.getPendingIdeas(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
