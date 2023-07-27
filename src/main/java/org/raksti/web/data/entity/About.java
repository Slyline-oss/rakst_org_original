package org.raksti.web.data.entity;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "about")
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String text;


    public About() {

    }

    public About(String title, String text) {
        this.title = title;
        this.text = text;
    }

    @NotNull
    public String[] split() {
        String[] result = StringUtils.split(text, "\n");
        if (result == null) {
            result = new String[1];
            result[0] = "";
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
