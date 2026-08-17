package com.example.consultantmanagementsystem.controller;

import com.example.consultantmanagementsystem.entity.User;
import com.example.consultantmanagementsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserManagementController {

    private final UserService userService;

    public UserManagementController(UserService userService) {
        this.userService = userService;
    }

    // Display all users
    @GetMapping
    public String viewUsers(HttpSession session, Model model) {

        String userRole = (String) session.getAttribute("userRole");

        System.out.println("USER ROLE IN SESSION = " + userRole);

        // Only ADMIN can access User Management
        if (!"ADMIN".equals(userRole)) {

            System.out.println("ACCESS DENIED - Redirecting to dashboard");

            return "redirect:/";
        }

        System.out.println("ACCESS GRANTED - ADMIN");

        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    // Show Add User form
    @GetMapping("/add")
    public String showAddUserForm(HttpSession session, Model model) {

        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "add-user";
    }

    // Save new user
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user,
                           HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/";
        }

        userService.saveUser(user);

        return "redirect:/users";
    }

    // Show Edit User form
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id,
                           HttpSession session,
                           Model model) {

        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/";
        }

        User user = userService.getUserById(id).orElse(null);

        if (user == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", user);

        return "add-user";
    }

    // Delete User
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            return "redirect:/";
        }

        userService.deleteUser(id);

        return "redirect:/users";
    }
}