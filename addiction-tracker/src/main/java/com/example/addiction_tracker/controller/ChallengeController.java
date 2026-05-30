package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.User;
import com.example.addiction_tracker.entity.UserChallenge;
import com.example.addiction_tracker.service.ChallengeService;
import com.example.addiction_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;
    private final UserService userService;

    @GetMapping
    public String challengesPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        List<UserChallenge> myChallenges = challengeService.findUserChallenges(user.getId());
        List<Long> myChallengeIds = myChallenges.stream()
                .map(uc -> uc.getChallenge().getId())
                .toList();

        Map<Long, Long> daysMap = new java.util.HashMap<>();
        for (UserChallenge uc : myChallenges) {
            daysMap.put(uc.getId(), challengeService.getDaysFromStart(uc));
        }

        model.addAttribute("allChallenges", challengeService.findAll());
        model.addAttribute("myChallenges", myChallenges);
        model.addAttribute("myChallengeIds", myChallengeIds);
        model.addAttribute("daysMap", daysMap);
        return "challenges";
    }

    @PostMapping("/join/{id}")
    public String joinChallenge(@AuthenticationPrincipal UserDetails userDetails,
                                @PathVariable Long id) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        challengeService.findById(id).ifPresent(challenge -> {
            challengeService.joinChallenge(user, challenge);
        });
        return "redirect:/challenges";
    }

    @PostMapping("/leave/{id}")
    public String leaveChallenge(@AuthenticationPrincipal UserDetails userDetails,
                                 @PathVariable Long id) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        List<UserChallenge> userChallenges = challengeService.findUserChallenges(user.getId());
        for (UserChallenge uc : userChallenges) {
            if (uc.getChallenge().getId().equals(id)) {
                challengeService.deleteUserChallenge(uc.getId());
                break;
            }
        }
        return "redirect:/challenges";
    }

    @PostMapping("/complete/{id}")
    public String completeChallenge(@PathVariable Long id) {
        challengeService.completeChallenge(id);
        return "redirect:/challenges";
    }
}