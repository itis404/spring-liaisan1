package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.Addiction;
import com.example.addiction_tracker.entity.User;
import com.example.addiction_tracker.repository.UserRepository;
import com.example.addiction_tracker.service.AddictionService;
import com.example.addiction_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/addictions")
@RequiredArgsConstructor
public class AddictionController {

    private final AddictionService addictionService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public String addictionsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Addiction> myAddictions = addictionService.findByUserId(user.getId());
        List<Long> myAddictionIds = myAddictions.stream()
                .map(Addiction::getId)
                .toList();
        model.addAttribute("myAddictions", myAddictions);
        model.addAttribute("myAddictionIds", myAddictionIds);
        model.addAttribute("allAddictions", addictionService.findAll());
        return "addictions";
    }

    @PostMapping("/add/{id}")
    public String addAddiction(@AuthenticationPrincipal UserDetails userDetails,
                               @PathVariable Long id) {
        User user = userRepository.findById(
                userService.findByUsername(userDetails.getUsername()).orElseThrow().getId()
        ).orElseThrow();

        addictionService.findById(id).ifPresent(addiction -> {
            if (user.getAddictions() == null) {
                user.setAddictions(new HashSet<>());
            }
            user.getAddictions().add(addiction);
            userRepository.save(user);
        });
        return "redirect:/addictions";
    }

    @PostMapping("/remove/{id}")
    public String removeAddiction(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable Long id) {
        User user = userRepository.findById(
                userService.findByUsername(userDetails.getUsername()).orElseThrow().getId()
        ).orElseThrow();

        addictionService.findById(id).ifPresent(addiction -> {
            if (user.getAddictions() != null) {
                user.getAddictions().removeIf(a -> a.getId().equals(addiction.getId()));
            }
            userRepository.save(user);
        });
        return "redirect:/addictions";
    }
}