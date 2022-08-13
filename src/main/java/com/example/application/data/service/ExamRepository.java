package com.example.application.data.service;

import com.example.application.views.newExam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, String> {

    Exam findByNaming(String naming);
    Exam findByFinished(boolean status);
    
}
