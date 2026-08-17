package com.example.consultantmanagementsystem.controller;

import com.example.consultantmanagementsystem.service.ConsultantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {

    private final ConsultantService consultantService;

    public ReportController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @GetMapping("/reports")
    public String reports(
            Model model,
            HttpSession session) {

        // Check if user is logged in
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        // Consultant statistics
        model.addAttribute(
                "totalConsultants",
                consultantService.getConsultantCount()
        );

        model.addAttribute(
                "activeConsultants",
                consultantService.getActiveConsultants()
        );

        model.addAttribute(
                "inactiveConsultants",
                consultantService.getInactiveConsultants()
        );

        model.addAttribute(
                "availableConsultants",
                consultantService.getAvailableConsultants()
        );

        model.addAttribute(
                "onProjectConsultants",
                consultantService.getOnProjectConsultants()
        );

        model.addAttribute(
                "newConsultants",
                consultantService.getNewConsultantsThisMonth()
        );

        // Technology statistics
        model.addAttribute(
                "technologyData",
                consultantService.getConsultantsByTechnology()
        );

        return "reports";
    }
}