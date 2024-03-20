package com.example.upvote.repository;

import com.example.upvote.model.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    Optional<Idea> findById(Long id);

    @Query("SELECT i FROM Idea i WHERE i.isApproved = true ORDER BY i.rating DESC")
    List<Idea> findIdeaByIsApprovedTrue();

    @Query("SELECT i FROM Idea i WHERE i.isApproved IS false")
    List<Idea> findIdeaByIsApprovedFalse();
}
