package com.example.application.data.service;

import com.example.application.data.entity.ExamData;
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

    public ExamData get(String email, String naming) {
        return examDataRepository.findByEmailAndNaming(email, naming);
    }

    public List<ExamData> get(boolean finished) {
        return examDataRepository.findAllByFinished(finished);
    }

    public void save(String email, String textData, String naming) {
        examDataRepository.save(new ExamData(email, textData, naming));
    }

    public void save(ExamData examData) {
        examDataRepository.save(examData);
    }

    public void save(String email, String textData, String naming, boolean finished) {
        examDataRepository.save(new ExamData(email, textData, naming, finished));
    }
}
