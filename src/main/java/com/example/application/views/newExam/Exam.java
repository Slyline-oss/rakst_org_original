package com.example.application.views.newExam;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "exams")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    private String naming;
    private String link;
    private String embedLink;
    private boolean finished;
    private double duration;

    public Exam(String naming, String link, String embedLink, boolean finished, double duration) {
        this.naming = naming;
        this.link = link;
        this.embedLink = embedLink;
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

    public String getEmbedLink() {
        return embedLink;
    }

    public void setEmbedLink(String embedLink) {
        this.embedLink = embedLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
