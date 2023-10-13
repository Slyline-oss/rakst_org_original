package org.raksti.web.data.service;

import org.raksti.web.views.newExam.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    public Optional<Exam> getById(Long id) {
        return examRepository.findById(id);
    }

    public void save(String naming, String link, boolean finished, boolean allowToShow, boolean allowToWrite) {
        examRepository.save(new Exam(naming, link, finished, allowToShow, allowToWrite));
    }

    public void save(Exam exam) {
        examRepository.save(exam);
    }

    public Exam getByFinished(boolean status) {
        return examRepository.findByFinished(status);
    }

    public List<Exam> get() {
        return examRepository.findAll();
    }
}
