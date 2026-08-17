package com.example.consultantmanagementsystem.controller;

import com.example.consultantmanagementsystem.entity.Consultant;
import com.example.consultantmanagementsystem.service.ConsultantService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class ExportController {

    private final ConsultantService consultantService;

    public ExportController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    // Export consultants to Excel
    @GetMapping("/export/excel")
    public void exportExcel(
            HttpServletResponse response,
            HttpSession session) throws IOException {

        // Check if user is logged in
        if (session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("/login");
            return;
        }

        // Get all consultants from database
        List<Consultant> consultants =
                consultantService.getAllConsultants();

        // Create Excel workbook
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Consultants");

        // Header row
        Row headerRow = sheet.createRow(0);

        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Phone");
        headerRow.createCell(4).setCellValue("Technology");
        headerRow.createCell(5).setCellValue("Experience");
        headerRow.createCell(6).setCellValue("Status");
        headerRow.createCell(7).setCellValue("Active");
        headerRow.createCell(8).setCellValue("Created Date");

        // Add consultant data
        int rowNumber = 1;

        for (Consultant consultant : consultants) {

            Row row = sheet.createRow(rowNumber++);

            row.createCell(0).setCellValue(
                    consultant.getId()
            );

            row.createCell(1).setCellValue(
                    consultant.getName()
            );

            row.createCell(2).setCellValue(
                    consultant.getEmail()
            );

            row.createCell(3).setCellValue(
                    consultant.getPhone()
            );

            row.createCell(4).setCellValue(
                    consultant.getTechnology()
            );

            row.createCell(5).setCellValue(
                    consultant.getExperience()
            );

            row.createCell(6).setCellValue(
                    consultant.getStatus()
            );

            row.createCell(7).setCellValue(
                    consultant.isActive() ? "Active" : "Inactive"
            );

            // Handle consultants with no created date
            row.createCell(8).setCellValue(
                    consultant.getCreatedDate() != null
                            ? consultant.getCreatedDate().toString()
                            : ""
            );
        }

        // Automatically adjust column widths
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }

        // Tell browser this is an Excel file
        response.setContentType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        );

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=consultants.xlsx"
        );

        // Send Excel file to browser
        workbook.write(response.getOutputStream());

        workbook.close();
    }
}