package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.entity.Addiction;
import com.example.addiction_tracker.service.AddictionService;
import com.example.addiction_tracker.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addictions")
@RequiredArgsConstructor
public class AddictionRestController {

    private final AddictionService addictionService;
    private final QuoteService quoteService;

    @GetMapping
    public List<Addiction> getAll() {
        return addictionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Addiction> getById(@PathVariable Long id) {
        return addictionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Addiction create(@RequestBody Addiction addiction) {
        return addictionService.save(addiction);
    }

    @GetMapping("/dollar-rate")
    public String getDollarRate() {
        return quoteService.getDollarRate();
    }
}