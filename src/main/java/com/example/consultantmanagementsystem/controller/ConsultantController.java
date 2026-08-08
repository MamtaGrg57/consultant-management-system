package com.example.consultantmanagementsystem.controller;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.example.consultantmanagementsystem.entity.Consultant;
import com.example.consultantmanagementsystem.service.ConsultantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ConsultantController {

    private final ConsultantService consultantService;

    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }


    // Display all consultants
    @GetMapping("/")
    public String viewConsultants(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("consultants",
                    consultantService.searchConsultants(keyword));
        } else {
            model.addAttribute("consultants",
                    consultantService.getAllConsultants());
        }

        model.addAttribute("keyword", keyword);

        model.addAttribute("totalConsultants",
                consultantService.getConsultantCount());

        model.addAttribute("activeConsultants",
                consultantService.getActiveConsultants());

        model.addAttribute("inactiveConsultants",
                consultantService.getInactiveConsultants());

        model.addAttribute("newConsultants",
                consultantService.getNewConsultantsThisMonth());

        return "consultants";
    }


    // Show Add Consultant form
    @GetMapping("/add")
    public String addConsultantForm(Model model) {
        model.addAttribute("consultant", new Consultant());
        return "add-consultant";
    }


    // Save Consultant
    @PostMapping("/save")
    public String saveConsultant(
            @Valid @ModelAttribute("consultant") Consultant consultant,
            BindingResult result) {

        if (result.hasErrors()) {
            return "add-consultant";
        }

        consultantService.saveConsultant(consultant);

        return "redirect:/";
    }


    // Delete Consultant
    @GetMapping("/delete/{id}")
    public String deleteConsultant(@PathVariable Long id) {
        consultantService.deleteConsultant(id);
        return "redirect:/";
    }


    // Edit Consultant - Open existing consultant data
    @GetMapping("/edit/{id}")
    public String editConsultant(@PathVariable Long id, Model model) {

        Consultant consultant = consultantService
                .getConsultantById(id)
                .orElse(null);

        model.addAttribute("consultant", consultant);

        return "add-consultant";
    }

    // Toggle Active / Inactive Status
    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Long id) {

        consultantService.toggleStatus(id);

        return "redirect:/";
    }
}