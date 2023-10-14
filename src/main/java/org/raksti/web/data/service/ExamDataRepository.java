package org.raksti.web.data.service;

import org.raksti.web.data.entity.ExamData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamDataRepository extends JpaRepository<ExamData, Long> {

    List<ExamData> findByEmail(String email);
    ExamData findByEmailAndExamId(String email, Long id);
    List<ExamData> findAllByFinished(boolean finished);
    List<ExamData> findAllByExamId(Long id);
}
