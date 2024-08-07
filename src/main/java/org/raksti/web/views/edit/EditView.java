package org.raksti.web.views.edit;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.Text;
import org.raksti.web.data.service.TextRepository;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.profile.ProfileViewService;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@PageTitle("Sūtīt svarīgu ziņojumu")
@Route(value = "edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EditView extends VerticalLayout {

    private Button submitButton;
    private final TextArea aboutTextArea;
    private final Select<String> historySelector;
    private final TextArea profileImportantMessageArea;
    private final Button sendButton;

    private final ProfileViewService profileViewService;

    private final TextRepository textRepository;

    public EditView(ProfileViewService profileViewService, TextRepository textRepository) {
        this.profileViewService = profileViewService;
        this.textRepository = textRepository;
        this.historySelector = new com.vaadin.flow.component.select.Select<>();
        this.aboutTextArea = new com.vaadin.flow.component.textfield.TextArea();
        this.profileImportantMessageArea = new TextArea();
        this.sendButton = new Button();


        createAboutFrontend();
        getStyle().set("padding-top", "30px");
        createNotificationFrontend();

        add(profileImportantMessageArea, sendButton);

        submitButton.addClickListener(e -> changeParagraph());
        sendButton.addClickListener(e -> changeNotification());
    }

    private void changeParagraph() {
        //aboutView.setText(aboutTextArea.getValue());
    }

    private void changeNotification() {
        profileViewService.setText(profileImportantMessageArea.getValue());
        Notification.show("Saglabāts", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
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
        aboutTextArea.setValue(text);
    }

    private Collection<String> getHistoryItems() {
        Collection<String> collection = new ArrayList<>();
        Optional<Text> maybeText = textRepository.findById("about");
        if (maybeText.isPresent()) {
            Text text = maybeText.get();
            collection.add(text.getContent_1());
            collection.add(text.getContent_2());
            collection.add(text.getContent_3());

            collection = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
        }
        return collection;
    }
}
