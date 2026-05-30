package com.example.addiction_tracker.service;

import com.example.addiction_tracker.entity.Challenge;
import com.example.addiction_tracker.entity.UserChallenge;
import com.example.addiction_tracker.entity.User;
import com.example.addiction_tracker.repository.ChallengeRepository;
import com.example.addiction_tracker.repository.UserChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserChallengeRepository userChallengeRepository;

    public List<Challenge> findAll() {
        return challengeRepository.findAll();
    }

    public Optional<Challenge> findById(Long id) {
        return challengeRepository.findById(id);
    }

    public Challenge save(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public void deleteById(Long id) {
        challengeRepository.deleteById(id);
    }

    public UserChallenge joinChallenge(User user, Challenge challenge) {
        UserChallenge uc = new UserChallenge();
        uc.setUser(user);
        uc.setChallenge(challenge);
        return userChallengeRepository.save(uc);
    }

    public List<UserChallenge> findUserChallenges(Long userId) {
        return userChallengeRepository.findByUserId(userId);
    }

    public void deleteUserChallenge(Long id) {
        userChallengeRepository.deleteById(id);
    }

    public void completeChallenge(Long userChallengeId) {
        userChallengeRepository.findById(userChallengeId).ifPresent(uc -> {
            uc.setCompleted(true);
            userChallengeRepository.save(uc);
        });
    }

    public long getDaysFromStart(UserChallenge uc) {
        return java.time.temporal.ChronoUnit.DAYS.between(
                uc.getStartedAt(), java.time.LocalDateTime.now()
        );
    }
}