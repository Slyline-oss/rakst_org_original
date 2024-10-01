package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.profile.ProfileView;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.UUID;

@PageTitle("Pieteikties rakstīšanai klātienē")
@Route(value = "offline-locations", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class OfflineLocationView extends VerticalLayout {
    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final User user;
    private final OfflineLocationService offlineLocationService;
    private final Grid<OfflineLocation> offlineExamGrid = new Grid<>(OfflineLocation.class);
    private final Dialog offlineLocationDialog = new Dialog();
    private final Dialog confirmationDialog = new Dialog();
    private final OfflineLocationForm offlineLocationForm = new OfflineLocationForm();

    @Autowired
    public OfflineLocationView(@NotNull AuthenticatedUser authenticatedUser,
                               @NotNull EmailSenderService emailSenderService,
                               @NotNull UserRepository userRepository,
                               @NotNull OfflineLocationService offlineLocationService) {
        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
        this.offlineLocationService = offlineLocationService;
        this.user = authenticatedUser.get().orElseThrow(() -> new IllegalStateException("No user found"));

        offlineLocationDialog.add(offlineLocationForm);
        configureForm();

        Button addOfflineLocationButton = new Button("Pievienot norises vietu", e -> addNewLocation());

        getData();
        add(offlineLocationDialog);
        if (user.getRoles().contains(Role.ADMIN)) {
            add(addOfflineLocationButton);
        }
        Div infoText = new Div();
        infoText.setText("Klātienes reģistrācija mājaslapā noslēgsies piektdien, 11. oktrobrī, pulksten 12.00. Tie, kuri nebūs paspējuši reģistrēties mājaslapā, to varēs izdarīt diktāta rakstīšanas vietā. Lai gan vietu skaits ir ierobežots, vēl nav bijusi reize, kurā kāds rakstīt gribētājs būtu aizsūtīts mājās");
        add(offlineExamGrid, infoText);
    }

    private void getData() {
        offlineExamGrid.removeAllColumns();
        offlineExamGrid.addColumn(OfflineLocation::getCountry).setHeader("Valsts").setSortable(true);
        offlineExamGrid.addColumn(OfflineLocation::getCity).setHeader("Pilsēta").setSortable(true);
        offlineExamGrid.addColumn(OfflineLocation::getAddress).setHeader("Adrese").setSortable(true);
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTotal).setHeader("Kopējais vietu skaits").setSortable(true);
        offlineExamGrid.addColumn(offlineLocation -> offlineLocation.getSlotsTotal() - offlineLocation.getSlotsTaken()).setHeader("Brīvo vietu skaits").setSortable(true);
        offlineExamGrid.addComponentColumn(offlineLocation -> {
            Button participate = new Button("Pieteikties");
            participate.addClickListener(buttonClickEvent -> participate(offlineLocation.getId()));
            return participate;
        });

        if (user.getRoles().contains(Role.ADMIN)) {
            offlineExamGrid.addComponentColumn(offlineLocation -> {
                Button editButton = new Button("Rediģēt");
                editButton.addClickListener(buttonClickEvent -> editOfflineLocation(offlineLocation));
                Button deleteButton = new Button("Dzēst");
                deleteButton.addClickListener(buttonClickEvent -> deleteOfflineLocation(offlineLocation));
                return new HorizontalLayout(editButton, deleteButton);
            }).setHeader("Admina funkcijas");
        }

        offlineExamGrid.getColumns().forEach(offlineLocationColumn -> offlineLocationColumn.setAutoWidth(true));
        offlineExamGrid.setMultiSort(true);

        updateList();
    }

    private void configureForm() {
        offlineLocationForm.addListener(OfflineLocationForm.SaveEvent.class, this::saveOfflineLocation);
        offlineLocationForm.addListener(OfflineLocationForm.CloseEvent.class, this::closeDialog);
    }

    private void saveOfflineLocation(OfflineLocationForm.SaveEvent event) {
        offlineLocationService.save(event.getOfflineLocation());
        offlineLocationDialog.close();
        updateList();
    }

    private void deleteOfflineLocation(OfflineLocation offlineLocation) {
        confirmationDialog.removeAll();
        Button confirm = new Button("Jā");
        confirm.getElement().getStyle().set("margin-left", "auto");
        confirm.addClickListener(buttonClickEvent -> {
            if (offlineLocation.getSlotsTaken() > 0) {
                showNotification("Nevar dzēst norises vietu, kur jau ir dalībnieki", NotificationVariant.LUMO_ERROR);
            } else {
                offlineLocationService.delete(offlineLocation);
                confirmationDialog.close();
                updateList();
            }
        });
        Button decline = new Button("Nē");
        decline.getElement().getStyle().set("margin-right", "auto");
        decline.addClickListener(buttonClickEvent -> {
            offlineLocationDialog.close();
            confirmationDialog.close();
        });
        confirmationDialog.add(new Paragraph("Dzēst norises vietu?"), new HorizontalLayout(confirm, decline));
        confirmationDialog.open();
    }

    private void closeDialog(OfflineLocationForm.CloseEvent event) {
        offlineLocationDialog.close();
    }

    private void editOfflineLocation(OfflineLocation offlineLocation) {
        if (offlineLocation == null) {
            offlineLocationDialog.close();
        } else {
            offlineLocationForm.setOfflineLocation(offlineLocation);
            offlineLocationDialog.open();
        }
    }

    private void addNewLocation() {
        editOfflineLocation(new OfflineLocation());
    }

    private void updateList() {
        offlineExamGrid.setItems(offlineLocationService.getAll());
    }

    @SuppressWarnings("ExtractMethodRecommender")
    private void participate(UUID offlineLocationId) {
        Dialog dialog = new Dialog();
        VerticalLayout vl = new VerticalLayout();

        OfflineLocation offlineLocation = offlineLocationService.getById(offlineLocationId);

        dialog.setHeaderTitle("Lūdzu apstipriniet");
        vl.add(new Label("Lūdzu apstipriniet diktātā piedalīšanos klātienē:"));
        vl.add(new Label(offlineLocation.getCity()));
        vl.add(new Label(offlineLocation.getAddress()));
        TextField note = new TextField("Informācija organizatoram");
        vl.add(note);

        Button cancel = new Button("Aizvērt");
        cancel.addClickListener(e -> dialog.close());

        Button ok = new Button("Piekrītu");
        ok.addClickListener(e -> {
            if (validateParticipationConditions(offlineLocation)) {
                user.setOfflineLocation(offlineLocation);
                user.setOfflineNote(note.getValue());
                userRepository.save(user);
                emailSenderService.sendEmail(user.getEmail(), getParticipationNotificationEmailBody(offlineLocation),
                        "Aicinām piedalīties IX. pasaules diktātā latviešu valodā!");
                offlineLocation.setSlotsTaken(offlineLocation.getSlotsTaken() + 1);
                offlineLocation.getParticipants().add(user);
                offlineLocationService.save(offlineLocation);
            }
            updateList();
            dialog.close();
            UI.getCurrent().navigate(ProfileView.class);
        });

        dialog.add(vl, new HorizontalLayout(cancel, ok));
        dialog.open();
    }

    private String getParticipationNotificationEmailBody(OfflineLocation offlineLocation) {
        String country = offlineLocation.getCountry();
        String city = offlineLocation.getCity();
        String address = offlineLocation.getAddress();
        return "Sveicināti!\n\n" +
                "Paldies, Jūsu pieteikums ir saņemts!\n" +
                "Gaidīsim Jūs 2023. gada 14. oktobrī plkst. 12.15 izvēlētajā rakstīšanas vietā!\n\n" +
                "Vārds: " + user.getFirstName() + "\n" +
                "Uzvārds: " + user.getLastName() + "\n" +
                (StringUtils.isNotBlank(user.getOfflineNote()) ? "Informācija organizatoram: " + user.getOfflineNote() + "\n" : "") +
                "Vieta: " + city + "\n" +
                "Adrese: " + address;
    }

    private void showNotification(String message) {
        Notification notification = Notification.show(message);
        notification.setPosition(Notification.Position.TOP_START);
    }

    private void showNotification(String message, NotificationVariant notificationVariant) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(notificationVariant);
        notification.setPosition(Notification.Position.TOP_START);
    }

    private boolean validateParticipationConditions(OfflineLocation offlineLocation) {
        if (offlineLocation.getSlotsTaken() >= offlineLocation.getSlotsTotal()) {
            showNotification("Nav vietu");
            return false;
        }
        if (user.getOfflineLocation() != null) {
            showNotification("Jūs jau piedalāties citā vietā!");
            return false;
        }
        return true;
    }
}
