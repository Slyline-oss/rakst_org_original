package com.example.application.data.service;

import com.example.application.views.newExam.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "examservice")
public class ExamService {

    private final ExamRepository examRepository;

    @Autowired
    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam get(String naming) {
       return examRepository.findByNaming(naming);
    }

    public void save(String naming, String link, boolean finished, double duration) {
        examRepository.save(new Exam(naming, link, finished, duration));
    }

    public void save(Exam exam) {
        examRepository.save(exam);
    }

    public Exam getByFinished(boolean status) {
        return examRepository.findByFinished(status);
    }
}
