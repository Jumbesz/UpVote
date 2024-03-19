package com.example.upvote.model;

import com.example.upvote.config.ProfileRole;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String username;

    private String password;

    private ProfileRole profileRole;

    @OneToMany
    @JoinColumn(name = "profile_id")
    private List<Idea> ideas;



}
