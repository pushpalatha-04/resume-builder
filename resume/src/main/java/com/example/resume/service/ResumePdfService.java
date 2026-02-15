package com.example.resume.service;

import com.example.resume.model.Resume;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ResumePdfService {

    public byte[] generatePdf(Resume resume) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Fonts
            var font = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // Header
            document.add(new Paragraph(resume.getFullName()).setFont(font).setFontSize(20));
            document.add(new Paragraph(resume.getEmail() + " | " + resume.getPhone() + " | " + resume.getCity()));
            document.add(new Paragraph("LinkedIn: " + resume.getLinkedin() + " | GitHub: " + resume.getGithub()));
            document.add(new Paragraph("\n"));

            // Sections
            document.add(new Paragraph("Career Objective").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getCareerObjective()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Education").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getEducation()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Technical Skills").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getTechnicalSkills()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Projects").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getProjects()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Internships").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getInternships()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Certifications").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getCertifications()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Soft Skills").setFont(font).setFontSize(14));
            document.add(new Paragraph(resume.getSoftSkills()));
            document.add(new Paragraph("\n"));

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
