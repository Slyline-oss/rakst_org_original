package org.raksti.web.views.newExam;

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
    private boolean allowToShow;
    private boolean allowToWrite;

    public Exam(String naming, String link, String embedLink, boolean finished, boolean allowToShow, boolean allowToWrite) {
        this.naming = naming;
        this.link = link;
        this.embedLink = embedLink;
        this.finished = finished;
        this.allowToShow = allowToShow;
        this.allowToWrite = allowToWrite;
    }


    public Exam() {

    }

    public boolean isAllowToWrite() {
        return allowToWrite;
    }

    public void setAllowToWrite(boolean allowToWrite) {
        this.allowToWrite = allowToWrite;
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

    public boolean isAllowToShow() {
        return allowToShow;
    }

    public void setAllowToShow(boolean allowToShow) {
        this.allowToShow = allowToShow;
    }
}
