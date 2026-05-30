package com.example.addiction_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 50, message = "Имя должно быть от 3 до 50 символов")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Пароль должен быть минимум 6 символов")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProgressEntry> progressEntries;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserChallenge> userChallenges;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_addictions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "addiction_id")
    )
    private Set<Addiction> addictions = new HashSet<>();
}