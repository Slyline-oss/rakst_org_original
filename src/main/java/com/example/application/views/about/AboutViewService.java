package com.example.application.views.about;

import com.example.application.data.entity.Text;
import com.example.application.data.service.TextRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AboutViewService {
    private String [] history;
    private final TextRepository textRepository;

    public AboutViewService(TextRepository textRepository) {
        this.textRepository = textRepository;
        textRepository.save(new Text("about"));
        this.history = new String[3];
    }

    public String getText() {
        return history[0];
    }

    public void setText(String text) {
        if (!Objects.equals(text, history[0])) {
            history[2] = history[1];
            history[1] = history[0];
            history[0] = text;
        }
        textRepository.setContentById(history[0], history[1], history[2], "about");
    }
}
