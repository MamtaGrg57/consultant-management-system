package com.example.consultantmanagementsystem.repository;

import com.example.consultantmanagementsystem.entity.Consultant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultantRepository extends JpaRepository<Consultant, Long> {


    List<Consultant> findByNameContainingIgnoreCaseOrTechnologyContainingIgnoreCase(
            String name,
            String technology
    );


    long countByActiveTrue();


    long countByActiveFalse();


    long countByCreatedDateAfter(LocalDate date);


}