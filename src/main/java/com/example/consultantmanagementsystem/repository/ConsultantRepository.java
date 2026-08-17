package com.example.consultantmanagementsystem.repository;

import com.example.consultantmanagementsystem.entity.Consultant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {

    // Search + Filter + Pagination
    Page<Consultant> findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
            String name,
            String technology,
            Pageable pageable
    );

    // Dashboard statistics
    long countByActiveTrue();

    long countByActiveFalse();

    long countByCreatedDateAfter(LocalDate date);

    long countByStatus(String status);

    Page<Consultant> findByTechnologyIgnoreCase(
            String technology,
            Pageable pageable
    );



    // Technology chart
    @Query("SELECT c.technology, COUNT(c) FROM Consultant c GROUP BY c.technology")
    List<Object[]> countConsultantsByTechnology();

}