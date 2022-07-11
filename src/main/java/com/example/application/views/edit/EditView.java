package com.example.application.views.edit;

import com.example.application.data.entity.Text;
import com.example.application.data.service.TextRepository;
import com.example.application.views.MainLayout;
import com.example.application.views.about.AboutViewService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.security.RolesAllowed;
import java.util.*;
import java.util.stream.Collectors;


@PageTitle("Edit View")
@Route(value = "edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EditView extends VerticalLayout {

    private com.vaadin.flow.component.button.Button submitButton;
    private com.vaadin.flow.component.textfield.TextArea aboutTextArea;
    private com.vaadin.flow.component.select.Select<String> historySelector;

    @Autowired
    private final AboutViewService aboutView;

    private final TextRepository textRepository;
    public EditView(AboutViewService aboutView, TextRepository textRepository) {
        this.aboutView = aboutView;
        this.textRepository = textRepository;
        this.historySelector = new com.vaadin.flow.component.select.Select<>();
        aboutTextArea = new com.vaadin.flow.component.textfield.TextArea();
        aboutTextArea.setLabel("Jaunumi");
        aboutTextArea.setWidthFull();
        aboutTextArea.setClearButtonVisible(true);
        submitButton = new com.vaadin.flow.component.button.Button("Saglabāt");
        historySelector.setLabel("Vēsture");
        historySelector.setItems(getHistoryItems());
        historySelector.setEmptySelectionAllowed(true);
        historySelector.addValueChangeListener(e -> returnText(e.getValue()));

        add(submitButton);
        add(aboutTextArea);
        add(historySelector);

        submitButton.addClickListener(e -> changeParagraph());
    }

    private void changeParagraph() {
        aboutView.setText(aboutTextArea.getValue());
    }

    private void returnText(String text) {
        if (!text.equals(null)) {
            aboutTextArea.setValue(text);
        }
    }

    private Collection<String> getHistoryItems() {
        Optional<Text> maybeText = textRepository.findById("about");
        Text text = null;
        if (maybeText.isPresent()) {
            text = maybeText.get();
        }
        Collection<String> collection = new ArrayList<>();
        collection.add(text.getContent_1());
        collection.add(text.getContent_2());
        collection.add(text.getContent_3());

        collection = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return collection;
    }
}
