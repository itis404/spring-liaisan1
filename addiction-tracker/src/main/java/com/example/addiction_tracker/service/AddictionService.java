package com.example.addiction_tracker.service;

import com.example.addiction_tracker.entity.Addiction;
import com.example.addiction_tracker.repository.AddictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddictionService {

    private final AddictionRepository addictionRepository;

    @Cacheable("addictions")
    public List<Addiction> findAll() {
        return addictionRepository.findAll();
    }

    public Optional<Addiction> findById(Long id) {
        return addictionRepository.findById(id);
    }

    @CacheEvict(value = "addictions", allEntries = true)
    public Addiction save(Addiction addiction) {
        return addictionRepository.save(addiction);
    }

    @CacheEvict(value = "addictions", allEntries = true)
    public void deleteById(Long id) {
        addictionRepository.deleteById(id);
    }

    public List<Addiction> findByUserId(Long userId) {
        return addictionRepository.findAddictionsByUserId(userId);
    }
}