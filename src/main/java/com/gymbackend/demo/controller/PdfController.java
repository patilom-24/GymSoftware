package com.gymbackend.demo.controller;

import com.gymbackend.demo.service.PdfGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf")
@CrossOrigin("*")
public class PdfController {

    private final PdfGeneratorService pdfGeneratorService;

    public PdfController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPlanPdf(
            @RequestParam String name,
            @RequestParam String contact,
            @RequestParam String plan,
            @RequestParam String imageBase64,
            @RequestParam String type) {

        // Generate PDF bytes
        byte[] pdfBytes = pdfGeneratorService.generatePlanPdf(name, contact, plan, imageBase64, type);

        // Construct the filename dynamically
        String filename = name + "_" + type + "_Plan.pdf";

        // Set headers for file download with dynamic filename
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
