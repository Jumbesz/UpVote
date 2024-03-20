package com.example.upvote.dto.outgoing;

import com.example.upvote.model.Idea;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PendingIdeasResponse {

    private List<Idea> pendingIdeas;


}
