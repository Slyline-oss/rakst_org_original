package com.example.application.views.about;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AboutViewService {
    private String text;

    public AboutViewService() {
        this.text = "It can be replaced";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
