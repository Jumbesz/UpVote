package com.example.upvote.service;

import com.example.upvote.dto.outgoing.ApprovedIdeasResponse;
import com.example.upvote.dto.outgoing.PendingIdeasResponse;
import com.example.upvote.model.Idea;
import com.example.upvote.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IdeaService {

    private final IdeaRepository ideaRepository;

    @Autowired
    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public String approveIdea(Long id) {

        Idea idea = ideaRepository.findById(id).orElseThrow();
        idea.setApproved(true);
        ideaRepository.save(idea);
        return idea.getName() + " is approved!";

    }

    public PendingIdeasResponse getPendingIdeas() {
        return new PendingIdeasResponse(ideaRepository.findIdeaByIsApprovedFalse());
    }

    public ApprovedIdeasResponse getApprovedIdeas() {
        return new ApprovedIdeasResponse(ideaRepository.findIdeaByIsApprovedTrue());
    }

    public Idea upvoteIdea(Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();
        idea.setRating(idea.getRating() + 1);
        ideaRepository.save(idea);
        return idea;
    }

    public Idea downVote(Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();
        idea.setRating(idea.getRating() - 1);
        ideaRepository.save(idea);
        return idea;
    }

    public String disapproveIdea(Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();
        ideaRepository.delete(idea);
        return idea + "has been deleted!";
    }
}
