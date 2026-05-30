package com.example.addiction_tracker.service;

import com.example.addiction_tracker.entity.ProgressEntry;
import com.example.addiction_tracker.repository.ProgressEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgressEntryService {

    private final ProgressEntryRepository progressEntryRepository;

    public ProgressEntry save(ProgressEntry entry) {
        return progressEntryRepository.save(entry);
    }

    public List<ProgressEntry> findByUserId(Long userId) {
        return progressEntryRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<ProgressEntry> findById(Long id) {
        return progressEntryRepository.findById(id);
    }

    public void deleteById(Long id) {
        progressEntryRepository.deleteById(id);
    }

    public Long countRelapses(Long userId) {
        return progressEntryRepository.countRelapsesByUserId(userId);
    }
}