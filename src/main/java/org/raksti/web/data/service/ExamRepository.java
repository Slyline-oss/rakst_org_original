package org.raksti.web.data.service;

import org.raksti.web.views.newExam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Exam findByNaming(String naming);

    Optional<Exam> findById(Long id);
    Exam findByFinished(boolean status);
    
}
