package com.example.application.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edit_table")
public class Text {

    public Text(String id, String content_1, String content_2, String content_3) {
        this.id = id;
        this.content_1 = content_1;
        this.content_2 = content_2;
        this.content_3 = content_3;
    }

    public Text(String content_1, String content_2, String content_3) {
        this.content_1 = content_1;
        this.content_2 = content_2;
        this.content_3 = content_3;
    }

    public Text(String id) {
        this.id = id;
    }

    @Id
    private String id;

    @Column(columnDefinition = "TEXT")
    private String content_1;
    @Column(columnDefinition = "TEXT")
    private String content_2;
    @Column(columnDefinition = "TEXT")
    private String content_3;

    public Text() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent_1() {
        return content_1;
    }

    public void setContent_1(String content_1) {
        this.content_1 = content_1;
    }

    public String getContent_2() {
        return content_2;
    }

    public void setContent_2(String content_2) {
        this.content_2 = content_2;
    }

    public String getContent_3() {
        return content_3;
    }

    public void setContent_3(String content_3) {
        this.content_3 = content_3;
    }
}
