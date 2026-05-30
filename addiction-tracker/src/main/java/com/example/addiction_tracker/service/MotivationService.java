package com.example.addiction_tracker.service;

import com.example.addiction_tracker.entity.Motivation;
import com.example.addiction_tracker.repository.MotivationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotivationService {

    private final MotivationRepository motivationRepository;

    public Motivation save(Motivation motivation) {
        return motivationRepository.save(motivation);
    }

    public List<Motivation> findByUserId(Long userId) {
        return motivationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public void deleteById(Long id) {
        motivationRepository.deleteById(id);
    }
}