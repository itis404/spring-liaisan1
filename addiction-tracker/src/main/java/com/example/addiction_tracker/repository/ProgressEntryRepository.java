package com.example.addiction_tracker.repository;

import com.example.addiction_tracker.entity.ProgressEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProgressEntryRepository extends JpaRepository<ProgressEntry, Long> {

    List<ProgressEntry> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("SELECT COUNT(p) FROM ProgressEntry p WHERE p.user.id = :userId AND p.relapsed = true")
    Long countRelapsesByUserId(@Param("userId") Long userId);
}