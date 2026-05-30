package com.example.addiction_tracker.repository;

import com.example.addiction_tracker.entity.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    List<UserChallenge> findByUserId(Long userId);
}