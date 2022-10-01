package org.raksti.web.views.edit;

import org.raksti.web.data.entity.Text;
import org.raksti.web.data.service.TextRepository;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.about.AboutViewService;
import org.raksti.web.views.profile.ProfileViewService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.security.RolesAllowed;
import java.util.*;
import java.util.stream.Collectors;


@PageTitle("Rediģēt sākumlapu")
@Route(value = "edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EditView extends VerticalLayout {

    private com.vaadin.flow.component.button.Button submitButton;
    private com.vaadin.flow.component.textfield.TextArea aboutTextArea;
    private com.vaadin.flow.component.select.Select<String> historySelector;
    private TextArea profileImportantMessageArea;
    private Button sendButton;

    @Autowired
    private final AboutViewService aboutView;
    private final ProfileViewService profileViewService;

    private final TextRepository textRepository;
    public EditView(AboutViewService aboutView, ProfileViewService profileViewService, TextRepository textRepository) {
        this.aboutView = aboutView;
        this.profileViewService = profileViewService;
        this.textRepository = textRepository;
        this.historySelector = new com.vaadin.flow.component.select.Select<>();
        this.aboutTextArea = new com.vaadin.flow.component.textfield.TextArea();
        this.profileImportantMessageArea = new TextArea();
        this.sendButton = new Button();


        createAboutFrontend();
        createNotificationFrontend();

        add(profileImportantMessageArea, sendButton);

        submitButton.addClickListener(e -> changeParagraph());
        sendButton.addClickListener(e -> changeNotification());
    }

    private void changeParagraph() {
        aboutView.setText(aboutTextArea.getValue());
    }

    private void changeNotification() {
        profileViewService.setText(profileImportantMessageArea.getValue());
    }

    private void createAboutFrontend() {
        aboutTextArea.setLabel("Jaunumi");
        aboutTextArea.setWidthFull();
        aboutTextArea.setClearButtonVisible(true);
        submitButton = new com.vaadin.flow.component.button.Button("Saglabāt");
        historySelector.setLabel("Vēsture");
        historySelector.setItems(getHistoryItems());
        historySelector.setEmptySelectionAllowed(true);
        historySelector.addValueChangeListener(e -> returnText(e.getValue()));
    }

    private void createNotificationFrontend() {
        profileImportantMessageArea.setLabel("Sūtīt svarīgu ziņojumu");
        profileImportantMessageArea.setWidthFull();
        profileImportantMessageArea.setClearButtonVisible(true);
        sendButton.setText("Sūtīt");
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
