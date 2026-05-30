package com.example.addiction_tracker.repository;

import com.example.addiction_tracker.entity.Addiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AddictionRepository extends JpaRepository<Addiction, Long> {

    @Query("SELECT a FROM Addiction a WHERE a.id IN " +
            "(SELECT ad.id FROM User u JOIN u.addictions ad WHERE u.id = :userId)")
    List<Addiction> findAddictionsByUserId(@org.springframework.data.repository.query.Param("userId") Long userId);
}