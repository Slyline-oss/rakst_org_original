package org.raksti.web.data.entity;

import javax.persistence.*;

@Table(name = "exam_data")
@Entity
public class ExamData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(columnDefinition = "TEXT")
    private String textData;
    private String result;
    private Long examId;
    private boolean finished;


    public ExamData(String email, String textData, Long examId) {
        this.email = email;
        this.textData = textData;
        this.examId = examId;
    }

    public ExamData(String email, String textData, Long examId, boolean finished) {
        this.email = email;
        this.textData = textData;
        this.examId = examId;
        this.finished = finished;
    }

    public ExamData() {
    }


    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long id) {
        this.examId = id;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(String textData) {
        this.textData = textData;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getId() {
        return examId;
    }

    public void setId(Long id) {
        this.examId = id;
    }
}
