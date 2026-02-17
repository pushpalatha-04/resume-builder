package com.example.resume.controller;

import com.example.resume.model.Resume;
import com.example.resume.service.ResumePdfService;
import com.example.resume.service.ResumeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins ={ "http://localhost:5713","https://resume-frontend-vite.onrender.com"})
public class ResumeController {

    private final ResumeService service;
    private final ResumePdfService pdfService;

    public ResumeController(ResumeService service, ResumePdfService pdfService) {
        this.service = service;
        this.pdfService = pdfService;
    }

    // Save Resume (Form Submission)
    @PostMapping("/default")
    public ResponseEntity<Resume> saveDefaultResume(@RequestBody Resume resume) {
        Resume saved = service.saveDefaultResume(resume);
        return ResponseEntity.ok(saved);
    }

    // Get All Resumes
    @GetMapping("/all")
    public ResponseEntity<List<Resume>> getAllResumes() {
        return ResponseEntity.ok(service.getAllResumes());
    }

    // Generate PDF Resume
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateResume(@RequestBody Resume resume) {

        // Save to database
        service.saveDefaultResume(resume);

        // Generate PDF
        byte[] pdfBytes = pdfService.generatePdf(resume);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
