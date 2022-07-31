package com.example.application.data.entity;

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
    private String naming;
    private boolean finished;

    public ExamData(String email, String textData, String naming) {
        this.email = email;
        this.textData = textData;
        this.naming = naming;
    }

    public ExamData(String email, String textData, String naming, boolean finished) {
        this.email = email;
        this.textData = textData;
        this.naming = naming;
        this.finished = finished;
    }

    public ExamData() {
    }


    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
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
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
