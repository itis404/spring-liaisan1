package com.example.addiction_tracker.controller;

import com.example.addiction_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        if (username == null || username.length() < 3) {
            model.addAttribute("error", "Имя пользователя должно быть минимум 3 символа!");
            return "auth/register";
        }
        if (password == null || password.length() < 6) {
            model.addAttribute("error", "Пароль должен быть минимум 6 символов!");
            return "auth/register";
        }
        if (!email.contains("@")) {
            model.addAttribute("error", "Некорректный email!");
            return "auth/register";
        }
        try {
            userService.register(username, email, password);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", "Пользователь с таким именем или email уже существует!");
            return "auth/register";
        }
    }
}