package com.example.resume.service;

import com.example.resume.model.Resume;
import com.example.resume.repository.ResumeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository repository;

    public ResumeService(ResumeRepository repository) {
        this.repository = repository;
    }

    // Save Resume
    public Resume saveDefaultResume(Resume resume) {
        return repository.save(resume);
    }

    // Get All Resumes
    public List<Resume> getAllResumes() {
        return repository.findAll();
    }
}
