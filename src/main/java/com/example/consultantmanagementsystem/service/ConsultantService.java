package com.example.consultantmanagementsystem.service;

import com.example.consultantmanagementsystem.entity.Consultant;
import com.example.consultantmanagementsystem.repository.ConsultantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultantService {

    private final ConsultantRepository consultantRepository;

    public ConsultantService(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    // Get all consultants
    public List<Consultant> getAllConsultants() {
        return consultantRepository.findAll();
    }

    // Get consultant by ID
    public Optional<Consultant> getConsultantById(Long id) {
        return consultantRepository.findById(id);
    }

    // Save consultant
    public Consultant saveConsultant(Consultant consultant) {
        return consultantRepository.save(consultant);
    }

    // Delete consultant
    public void deleteConsultant(Long id) {
        consultantRepository.deleteById(id);
    }
    // Search consultants by name or technology
    public List<Consultant> searchConsultants(String keyword) {

        return consultantRepository
                .findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
                        keyword,
                        keyword
                );
    }
    // Get total consultant count
    public long getConsultantCount() {
        return consultantRepository.count();
    }
    // Count active consultants
    public long getActiveConsultants() {
        return consultantRepository.countByActiveTrue();
    }


    // Count inactive consultants
    public long getInactiveConsultants() {
        return consultantRepository.countByActiveFalse();
    }


    // Count consultants added this month
    public long getNewConsultantsThisMonth() {

        LocalDate firstDayOfMonth =
                LocalDate.now().withDayOfMonth(1);

        return consultantRepository.countByCreatedDateAfter(firstDayOfMonth);
    }
    // Toggle consultant active status
    public void toggleStatus(Long id) {

        Consultant consultant = consultantRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Consultant not found"));

        consultant.setActive(!consultant.isActive());

        consultantRepository.save(consultant);
    }

}