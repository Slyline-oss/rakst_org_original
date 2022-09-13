package org.raksti.web.data.service;

import org.raksti.web.views.newExam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, String> {

    Exam findByNaming(String naming);
    Exam findByFinished(boolean status);
    
}
