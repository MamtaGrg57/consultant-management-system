package com.example.consultantmanagementsystem.controller;

import com.example.consultantmanagementsystem.entity.Consultant;
import com.example.consultantmanagementsystem.service.ConsultantService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ConsultantController {

    private final ConsultantService consultantService;

    public ConsultantController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    // Display all consultants with search, sorting and pagination
    @GetMapping("/")
    public String viewConsultants(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "technology", required = false) String technology,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            HttpSession session) {

        // Check if user is logged in
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        // Sorting
        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sort = Sort.by(sortDirection, sortBy);

        // Pagination
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Consultant> consultantPage;

// Search + Technology Filter + Pagination
        if (technology != null && !technology.trim().isEmpty()) {

            consultantPage =
                    consultantService.filterByTechnology(
                            technology.trim(),
                            pageable
                    );

        } else if (keyword != null && !keyword.trim().isEmpty()) {

            consultantPage =
                    consultantService.searchConsultants(
                            keyword.trim(),
                            pageable
                    );

        } else {

            consultantPage =
                    consultantService.getAllConsultants(pageable);
        }

        // Consultants displayed in table
        model.addAttribute(
                "consultants",
                consultantPage.getContent()
        );

        // Pagination information
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", consultantPage.getTotalPages());
        model.addAttribute("totalItems", consultantPage.getTotalElements());

        // Search and sorting information
        model.addAttribute("keyword", keyword);

        // Notification message
        String successMessage =
                (String) session.getAttribute("successMessage");

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }

        model.addAttribute("technology", technology);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("size", size);

        // Dashboard statistics
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
                "newConsultants",
                consultantService.getNewConsultantsThisMonth()
        );

        model.addAttribute(
                "availableConsultants",
                consultantService.getAvailableConsultants()
        );

        model.addAttribute(
                "onProjectConsultants",
                consultantService.getOnProjectConsultants()
        );

        // Technology chart
        model.addAttribute(
                "technologyData",
                consultantService.getConsultantsByTechnology()
        );

        return "consultants";
    }

    // Show Add Consultant form
    @GetMapping("/add")
    public String addConsultantForm(Model model,
                                    HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        model.addAttribute("consultant", new Consultant());

        return "add-consultant";
    }

    // Save Consultant
    @PostMapping("/save")
    public String saveConsultant(
            @Valid @ModelAttribute("consultant") Consultant consultant,
            BindingResult result,
            HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "add-consultant";
        }

        boolean isNew = consultant.getId() == null;

        consultantService.saveConsultant(consultant);

        if (isNew) {
            session.setAttribute("successMessage",
                    "✅ Consultant added successfully!");
        } else {
            session.setAttribute("successMessage",
                    "✏️ Consultant updated successfully!");
        }

        return "redirect:/";
    }

    // Delete Consultant
    @GetMapping("/delete/{id}")
    public String deleteConsultant(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        consultantService.deleteConsultant(id);

        session.setAttribute("successMessage",
                "🗑️ Consultant deleted successfully!");

        return "redirect:/";
    }

    // Edit Consultant
    @GetMapping("/edit/{id}")
    public String editConsultant(
            @PathVariable Long id,
            Model model,
            HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        Consultant consultant = consultantService
                .getConsultantById(id)
                .orElse(null);

        model.addAttribute("consultant", consultant);

        return "add-consultant";
    }

    // Toggle Active / Inactive Status
    @GetMapping("/toggle-status/{id}")
    public String toggleStatus(
            @PathVariable Long id,
            HttpSession session) {

        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        consultantService.toggleStatus(id);

        return "redirect:/";
    }
}