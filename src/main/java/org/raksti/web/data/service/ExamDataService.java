package org.raksti.web.data.service;

import org.raksti.web.data.entity.ExamData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "examdataservice")
public class ExamDataService {

    private final ExamDataRepository examDataRepository;

    public ExamDataService(ExamDataRepository examDataRepository) {
        this.examDataRepository = examDataRepository;
    }

    public List<ExamData> get(String email) {
        return examDataRepository.findByEmail(email);
    }

    public ExamData get(String email, Long examId) {
        return examDataRepository.findByEmailAndExamId(email, examId);
    }

    public List<ExamData> get(boolean finished) {
        return examDataRepository.findAllByFinished(finished);
    }

    public List<ExamData> get(Long examId) {
        return examDataRepository.findAllByExamId(examId);
    }

    public void save(String email, String textData, Long examId) {
        examDataRepository.save(new ExamData(email, textData, examId));
    }

    public void save(ExamData examData) {
        examDataRepository.save(examData);
    }

    public void save(String email, String textData, Long examId, boolean finished) {
        examDataRepository.save(new ExamData(email, textData, examId, finished));
    }
}
