package com.example.application.views.edit;

import com.example.application.views.MainLayout;
import com.example.application.views.about.AboutView;
import com.example.application.views.about.AboutViewService;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.security.RolesAllowed;


@PageTitle("Edit View")
@Route(value = "edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EditView extends VerticalLayout {

    private com.vaadin.flow.component.button.Button submitButton;
    private com.vaadin.flow.component.textfield.TextArea aboutTextArea;

    @Autowired
    private final AboutViewService aboutView;

    public EditView(AboutViewService aboutView) {
        this.aboutView = aboutView;
        aboutTextArea = new com.vaadin.flow.component.textfield.TextArea();
        aboutTextArea.setLabel("Jaunumi");
        aboutTextArea.setWidthFull();
        aboutTextArea.setClearButtonVisible(true);
        submitButton = new com.vaadin.flow.component.button.Button("Saglabāt");

        add(submitButton);
        add(aboutTextArea);

        submitButton.addClickListener(e -> changeParagraph());
    }

    private void changeParagraph() {
        aboutView.setText(aboutTextArea.getValue());
    }
}
