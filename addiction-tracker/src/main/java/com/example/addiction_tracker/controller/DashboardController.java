package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.User;
import com.example.addiction_tracker.service.AddictionService;
import com.example.addiction_tracker.service.ProgressEntryService;
import com.example.addiction_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final ProgressEntryService progressEntryService;
    private final AddictionService addictionService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("entries", progressEntryService.findByUserId(user.getId()));
        model.addAttribute("addictions", user.getAddictions());
        return "dashboard";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}