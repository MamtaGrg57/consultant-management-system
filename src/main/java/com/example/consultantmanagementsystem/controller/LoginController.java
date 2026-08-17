package com.example.consultantmanagementsystem.controller;

import com.example.consultantmanagementsystem.entity.User;
import com.example.consultantmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        Optional<User> userOptional = userService.findByUsername(username);

        if (userOptional.isPresent() &&
                userOptional.get().getPassword().equals(password)) {

            User user = userOptional.get();

            session.setAttribute("loggedInUser", user.getUsername());
            session.setAttribute("userRole", user.getRole());

            return "redirect:/";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}