package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.Motivation;
import com.example.addiction_tracker.entity.User;
import com.example.addiction_tracker.service.MotivationService;
import com.example.addiction_tracker.service.QuoteService;
import com.example.addiction_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motivations")
@RequiredArgsConstructor
public class MotivationController {

    private final MotivationService motivationService;
    private final UserService userService;

    @GetMapping
    public String motivationsPage(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("motivations", motivationService.findByUserId(user.getId()));
        return "motivations";
    }

    @PostMapping("/add")
    public String addMotivation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String text) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        Motivation motivation = new Motivation();
        motivation.setUser(user);
        motivation.setText(text);
        motivationService.save(motivation);
        return "redirect:/motivations";
    }

    @PostMapping("/delete/{id}")
    public String deleteMotivation(@PathVariable Long id) {
        motivationService.deleteById(id);
        return "redirect:/motivations";
    }
}