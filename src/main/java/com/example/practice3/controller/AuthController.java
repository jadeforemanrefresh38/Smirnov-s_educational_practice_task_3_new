package com.example.practice3.controller;

import com.example.practice3.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login-form";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, Model model) {
        if (userService.authenticate(username, password)) {
            session.setAttribute("loggedIn", true);
            return "redirect:/students";
        }
        model.addAttribute("error", "Неверный логин или пароль");
        return "login-form";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                           Model model) {
        if (username.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Заполните логин и пароль");
            return "register-form";
        }
        if (!userService.register(username, password)) {
            model.addAttribute("error", "Этот логин уже занят");
            return "register-form";
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
