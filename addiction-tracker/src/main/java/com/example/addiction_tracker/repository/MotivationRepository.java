package com.example.addiction_tracker.repository;

import com.example.addiction_tracker.entity.Motivation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MotivationRepository extends JpaRepository<Motivation, Long> {

    List<Motivation> findByUserIdOrderByCreatedAtDesc(Long userId);
}