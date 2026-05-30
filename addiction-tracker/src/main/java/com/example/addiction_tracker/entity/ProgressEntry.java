package com.example.addiction_tracker.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "progress_entries")
public class ProgressEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "addiction_id", nullable = false)
    private Addiction addiction;

    private String note;

    private boolean relapsed;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}