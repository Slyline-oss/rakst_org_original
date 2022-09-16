package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.UUID;

@PageTitle("Offline Locations")
@Route(value = "offline-locations", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class OfflineLocationView extends VerticalLayout {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final AuthenticatedUser authenticatedUser;
    private final UserRepository userRepository;
    private final User user;
    private final OfflineLocationService offlineLocationService;
    private final Grid<OfflineLocation> offlineExamGrid = new Grid<>(OfflineLocation.class);
    private final Dialog offlineLocationDialog = new Dialog();
    private final Dialog confirmationDialog = new Dialog();
    private final OfflineLocationForm offlineLocationForm = new OfflineLocationForm();

    public OfflineLocationView(AuthenticatedUser authenticatedUser, UserRepository userRepository, OfflineLocationService offlineLocationService) {
        this.authenticatedUser = authenticatedUser;
        this.userRepository = userRepository;
        this.offlineLocationService = offlineLocationService;
        this.user = authenticatedUser.get().get();

        offlineLocationDialog.add(offlineLocationForm);
        configureForm();

        Button addOfflineLocationButton = new Button("Add Location", e -> addNewLocation());

        getData();
        add(offlineLocationDialog);
        if (authenticatedUser.get().get().getRoles().contains(Role.ADMIN)) {
            add(addOfflineLocationButton);
        }
        add(offlineExamGrid);
    }

    private void getData() {
        offlineExamGrid.removeAllColumns();
        offlineExamGrid.addColumn(OfflineLocation::getCountry).setHeader("Country").setSortable(true);
        offlineExamGrid.addColumn(OfflineLocation::getCity).setHeader("City").setSortable(true);
        offlineExamGrid.addColumn(OfflineLocation::getAddress).setHeader("Address").setSortable(true);
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTotal).setHeader("Slots Total").setSortable(true);
        offlineExamGrid.addColumn(offlineLocation -> offlineLocation.getSlotsTotal() - offlineLocation.getSlotsTaken()).setHeader("Slots Remaining").setSortable(true);
        offlineExamGrid.addComponentColumn(offlineLocation -> {
            Button participate = new Button("participate");
            participate.addClickListener(buttonClickEvent -> participate(offlineLocation.getId()));
            return participate;
        });

        if (authenticatedUser.get().get().getRoles().contains(Role.ADMIN)) {
            offlineExamGrid.addComponentColumn(offlineLocation -> {
                Button editButton = new Button("edit");
                editButton.addClickListener(buttonClickEvent -> editOfflineLocation(offlineLocation));
                Button deleteButton = new Button("delete");
                deleteButton.addClickListener(buttonClickEvent -> deleteOfflineLocation(offlineLocation));
                return new HorizontalLayout(editButton, deleteButton);
            }).setHeader("Admin Actions");
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
        Button confirm = new Button("Yes");
        confirm.getElement().getStyle().set("margin-left", "auto");
        confirm.addClickListener(buttonClickEvent -> {
            if (offlineLocation.getSlotsTaken() > 0) {
                showNotification("Cannot delete location with active participants!", NotificationVariant.LUMO_ERROR);
            } else {
                offlineLocationService.delete(offlineLocation);
                confirmationDialog.close();
                updateList();
            }
        });
        Button decline = new Button("No");
        decline.getElement().getStyle().set("margin-right", "auto");
        decline.addClickListener(buttonClickEvent -> {
            offlineLocationDialog.close();
            confirmationDialog.close();
        });
        confirmationDialog.add(new Paragraph("Are you sure you want fo delete this location?"), new HorizontalLayout(confirm, decline));
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

    private void participate(UUID offlineLocationId) {
        OfflineLocation offlineLocation = offlineLocationService.getById(offlineLocationId);
        if (validateParticipationConditions(offlineLocation)) {
            user.setOfflineLocation(offlineLocation);
            userRepository.save(user);
            //TODO: implement email notification sending

            offlineLocation.setSlotsTaken(offlineLocation.getSlotsTaken()+1);
            offlineLocation.getParticipants().add(user);
            offlineLocationService.save(offlineLocation);
            showNotification("upd, slots taken: " + offlineLocation.getSlotsTaken());
        }
        updateList();
    }

    private void showNotification(String message) {
        Notification notification = Notification.show(message);
    }

    private void showNotification(String message, NotificationVariant notificationVariant) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(notificationVariant);
    }

    private boolean validateParticipationConditions(OfflineLocation offlineLocation) {
        if (offlineLocation.getSlotsTaken() >= offlineLocation.getSlotsTotal()) {
            showNotification("out of slots");
            return false;
        }
        if (user.getOfflineLocation() != null) {
            showNotification("Already participating in another location!");
            return false;
        }
        return true;
    }
}
