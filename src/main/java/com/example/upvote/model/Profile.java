package com.example.upvote.model;

import com.example.upvote.config.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false, unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private ProfileRole profileRole;

    @OneToMany
    @JoinColumn(name = "profile_id")
    private List<Idea> ideas = new ArrayList<>();


}
