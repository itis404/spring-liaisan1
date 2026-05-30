package com.example.addiction_tracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "challenges")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private int durationDays;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    private List<UserChallenge> userChallenges;
}