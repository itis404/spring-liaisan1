package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.Addiction;
import com.example.addiction_tracker.entity.Challenge;
import com.example.addiction_tracker.service.AddictionService;
import com.example.addiction_tracker.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AddictionService addictionService;
    private final ChallengeService challengeService;

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("addictions", addictionService.findAll());
        model.addAttribute("challenges", challengeService.findAll());
        return "admin";
    }

    @PostMapping("/addictions/add")
    public String addAddiction(@RequestParam String name,
                               @RequestParam String description) {
        Addiction addiction = new Addiction();
        addiction.setName(name);
        addiction.setDescription(description);
        addictionService.save(addiction);
        return "redirect:/admin";
    }

    @PostMapping("/addictions/delete/{id}")
    public String deleteAddiction(@PathVariable Long id) {
        addictionService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/challenges/add")
    public String addChallenge(@RequestParam String title,
                               @RequestParam String description,
                               @RequestParam int durationDays) {
        Challenge challenge = new Challenge();
        challenge.setTitle(title);
        challenge.setDescription(description);
        challenge.setDurationDays(durationDays);
        challengeService.save(challenge);
        return "redirect:/admin";
    }

    @PostMapping("/challenges/delete/{id}")
    public String deleteChallenge(@PathVariable Long id) {
        challengeService.deleteById(id);
        return "redirect:/admin";
    }
}