package com.example.upvote.dto.outgoing;

import com.example.upvote.model.Idea;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApprovedIdeasResponse {

    private List<Idea> approvedIdeas;

}
