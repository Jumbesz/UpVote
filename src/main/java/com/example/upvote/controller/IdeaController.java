package com.example.upvote.controller;

import com.example.upvote.dto.outgoing.ApprovedIdeasResponse;
import com.example.upvote.model.Idea;
import com.example.upvote.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/idea")
public class IdeaController {

    private final IdeaService ideaService;
    private final HttpServletRequest request;

    @Autowired
    public IdeaController(IdeaService ideaService, HttpServletRequest request) {
        this.ideaService = ideaService;
        this.request = request;
    }

    @GetMapping
    public ResponseEntity<ApprovedIdeasResponse> getApprovedIdeas() {
        return new ResponseEntity<>(ideaService.getApprovedIdeas(), HttpStatus.OK);
    }

    @PutMapping("/{id}/upvote")
    public ResponseEntity<String> upvoteIdea(@PathVariable Long id) {
        HttpSession session = request.getSession(true);
        if (session != null && session.getAttribute("voted") != null) {
            return new ResponseEntity<>("You have already voted in this session", HttpStatus.BAD_REQUEST);
        }

        Idea idea = ideaService.upvoteIdea(id);
        if (idea != null) {
            session.setAttribute("voted", true);
            return new ResponseEntity<>("Thank you for your vote!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Idea not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/downvote")
    public ResponseEntity<String> downVoteIdea(@PathVariable Long id) {
        HttpSession session = request.getSession(true);
        if (session != null && session.getAttribute("voted") != null) {
            return new ResponseEntity<>("You have already voted in this session", HttpStatus.BAD_REQUEST);
        }

        Idea idea = ideaService.downVote(id);
        if (idea != null) {
            session.setAttribute("voted", true);
            return new ResponseEntity<>("Thank you for your vote!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Idea not found", HttpStatus.NOT_FOUND);
        }
    }
}
