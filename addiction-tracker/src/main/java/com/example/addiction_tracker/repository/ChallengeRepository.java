package com.example.addiction_tracker.repository;

import com.example.addiction_tracker.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}