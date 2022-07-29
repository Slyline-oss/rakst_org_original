package com.example.application.views.newExam;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    private String naming;

    private String link;
    private boolean finished;
    private double duration;

    public Exam(String naming, String link, boolean finished, double duration) {
        this.naming = naming;
        this.link = link;
        this.finished = finished;
        this.duration = duration;
    }

    public Exam() {

    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
