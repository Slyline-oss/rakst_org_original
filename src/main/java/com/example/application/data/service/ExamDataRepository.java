package com.example.application.data.service;

import com.example.application.data.entity.ExamData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamDataRepository extends JpaRepository<ExamData, Long> {

    List<ExamData> findByEmail(String email);
    ExamData findByEmailAndNaming(String email, String naming);
    List<ExamData> findAllByFinished(boolean finished);
}
