package com.example.upvote.service;

import com.example.upvote.model.Idea;
import com.example.upvote.repository.IdeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IdeaService {

    private IdeaRepository ideaRepository;

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

    public List<Idea> getPendingIdeas() {
        return ideaRepository.findIdeaByIsApprovedFalse();
    }

    public List<Idea> getApprovedIdeas() {
        return ideaRepository.findIdeaByIsApprovedTrue();
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
}
