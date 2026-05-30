package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.ProgressEntry;
import com.example.addiction_tracker.entity.User;
import com.example.addiction_tracker.service.AddictionService;
import com.example.addiction_tracker.service.ProgressEntryService;
import com.example.addiction_tracker.service.QuoteService;
import com.example.addiction_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressEntryService progressEntryService;
    private final UserService userService;
    private final AddictionService addictionService;
    private final QuoteService quoteService;

    @GetMapping
    public String progressPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("entries", progressEntryService.findByUserId(user.getId()));
        model.addAttribute("addictions", addictionService.findByUserId(user.getId()));
        model.addAttribute("relapseCount", progressEntryService.countRelapses(user.getId()));
        model.addAttribute("dollarRate", quoteService.getDollarRate());
        return "progress";
    }

    @PostMapping("/add")
    public String addEntry(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam Long addictionId,
                           @RequestParam String note,
                           @RequestParam(defaultValue = "false") boolean relapsed) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        addictionService.findById(addictionId).ifPresent(addiction -> {
            ProgressEntry entry = new ProgressEntry();
            entry.setUser(user);
            entry.setAddiction(addiction);
            entry.setNote(note);
            entry.setRelapsed(relapsed);
            progressEntryService.save(entry);
        });
        return "redirect:/progress";
    }

    @PostMapping("/delete/{id}")
    public String deleteEntry(@PathVariable Long id) {
        progressEntryService.deleteById(id);
        return "redirect:/progress";
    }
    @GetMapping("/edit/{id}")
    public String editEntryPage(@PathVariable Long id, Model model,
                                @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        progressEntryService.findById(id).ifPresent(entry -> {
            model.addAttribute("entry", entry);
            model.addAttribute("addictions", addictionService.findByUserId(user.getId()));
        });
        return "progress-edit";
    }

    @PostMapping("/edit/{id}")
    public String editEntry(@PathVariable Long id,
                            @RequestParam Long addictionId,
                            @RequestParam String note,
                            @RequestParam(defaultValue = "false") boolean relapsed) {
        progressEntryService.findById(id).ifPresent(entry -> {
            addictionService.findById(addictionId).ifPresent(entry::setAddiction);
            entry.setNote(note);
            entry.setRelapsed(relapsed);
            progressEntryService.save(entry);
        });
        return "redirect:/progress";
    }
}