package com.example.consultantmanagementsystem.controller;

import com.example.consultantmanagementsystem.entity.Consultant;
import com.example.consultantmanagementsystem.service.ConsultantService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class PdfExportController {

    private final ConsultantService consultantService;

    public PdfExportController(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    @GetMapping("/export/pdf")
    public void exportPdf(
            HttpServletResponse response,
            HttpSession session) throws IOException {

        // Check if user is logged in
        if (session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("/login");
            return;
        }

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=consultants.pdf"
        );

        List<Consultant> consultants =
                consultantService.getAllConsultants();

        // Landscape page gives the table more horizontal space
        Document document =
                new Document(PageSize.A4.rotate());

        PdfWriter.getInstance(
                document,
                response.getOutputStream()
        );

        document.open();

        // Title
        Font titleFont = new Font(
                Font.HELVETICA,
                18,
                Font.BOLD
        );

        Paragraph title =
                new Paragraph(
                        "Consultant Management Report",
                        titleFont
                );

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);

        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage(100);

        // Set column widths
        table.setWidths(new float[]{
                0.6f,
                1.5f,
                2.3f,
                1.5f,
                1.8f,
                1.0f,
                1.3f
        });

        Font headerFont = new Font(
                Font.HELVETICA,
                10,
                Font.BOLD
        );

        Font dataFont = new Font(
                Font.HELVETICA,
                9
        );

        // Header cells
        addHeaderCell(table, "ID", headerFont);
        addHeaderCell(table, "Name", headerFont);
        addHeaderCell(table, "Email", headerFont);
        addHeaderCell(table, "Phone", headerFont);
        addHeaderCell(table, "Technology", headerFont);
        addHeaderCell(table, "Experience", headerFont);
        addHeaderCell(table, "Status", headerFont);

        // Consultant data
        for (Consultant consultant : consultants) {

            addDataCell(
                    table,
                    String.valueOf(consultant.getId()),
                    dataFont
            );

            addDataCell(
                    table,
                    consultant.getName(),
                    dataFont
            );

            addDataCell(
                    table,
                    consultant.getEmail(),
                    dataFont
            );

            addDataCell(
                    table,
                    consultant.getPhone(),
                    dataFont
            );

            addDataCell(
                    table,
                    consultant.getTechnology(),
                    dataFont
            );

            addDataCell(
                    table,
                    consultant.getExperience() + " Years",
                    dataFont
            );

            addDataCell(
                    table,
                    consultant.getStatus(),
                    dataFont
            );
        }

        document.add(table);

        document.close();
    }

    // Header cell
    private void addHeaderCell(
            PdfPTable table,
            String text,
            Font font) {

        PdfPCell cell =
                new PdfPCell(new Phrase(text, font));

        cell.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );

        cell.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        cell.setPadding(6);

        table.addCell(cell);
    }

    // Data cell
    private void addDataCell(
            PdfPTable table,
            String text,
            Font font) {

        PdfPCell cell =
                new PdfPCell(new Phrase(text, font));

        cell.setVerticalAlignment(
                Element.ALIGN_MIDDLE
        );

        cell.setPadding(5);

        table.addCell(cell);
    }
}