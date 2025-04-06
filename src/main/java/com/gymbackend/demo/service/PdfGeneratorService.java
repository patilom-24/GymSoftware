package com.gymbackend.demo.service;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;

@Service
public class PdfGeneratorService {

    private final TemplateEngine templateEngine;

    public PdfGeneratorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePlanPdf(String name, String contact, String plan, String imageBase64, String type) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("contact", contact);
        context.setVariable("plan", plan);
        context.setVariable("imageBase64", imageBase64);

        // Default template selection
        String template = "diet-plan"; // Default to diet-plan
        if ("Workout".equalsIgnoreCase(type)) {
            template = "workout-plan";
        }

        try {
            String htmlContent = templateEngine.process(template, context);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ConverterProperties properties = new ConverterProperties();

            HtmlConverter.convertToPdf(htmlContent, outputStream, properties);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace(); // Log error for debugging
            return null; // Return null or throw a custom exception
        }
    }
}
