package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Offline Locations Admin")
@Route(value = "offline-locations-admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OfflineLocationAdminView extends VerticalLayout {
    private final OfflineLocationService offlineLocationService;
    private final Grid<OfflineLocation> offlineExamGrid = new Grid<>(OfflineLocation.class);
    private final Dialog dialog = new Dialog();
    private OfflineLocationForm offlineLocationForm = new OfflineLocationForm();

    public OfflineLocationAdminView(OfflineLocationService offlineLocationService) {
        this.offlineLocationService = offlineLocationService;

        dialog.add(offlineLocationForm);
        configureForm();

        Button addOfflineLocationButton = new Button("Add Location", e -> addNewLocation());

        getData();
        add(dialog, addOfflineLocationButton, offlineExamGrid);
    }

    private void getData() {
        offlineExamGrid.removeAllColumns();
        offlineExamGrid.addColumn(OfflineLocation::getCity).setHeader("City");
        offlineExamGrid.addColumn(OfflineLocation::getAddress).setHeader("Address");
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTotal).setHeader("Slots Total");
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTaken).setHeader("Slots Taken");

        offlineExamGrid.addComponentColumn(offlineLocation -> {
            Button editButton = new Button("edit");
            editButton.addClickListener(buttonClickEvent -> editOfflineLocation(offlineLocation));
            Button deleteButton = new Button("delete");
            deleteButton.addClickListener(buttonClickEvent -> deleteOfflineLocation(offlineLocation));
            return new HorizontalLayout(editButton, deleteButton);
        }).setHeader("Action");

        updateList();

    }

    private void configureForm() {
        offlineLocationForm.addListener(OfflineLocationForm.SaveEvent.class, this::saveOfflineLocation);
        offlineLocationForm.addListener(OfflineLocationForm.CloseEvent.class, this::closeDialog);
    }

    private void saveOfflineLocation(OfflineLocationForm.SaveEvent event) {
        offlineLocationService.save(event.getOfflineLocation());
        dialog.close();
        updateList();
    }

    private void deleteOfflineLocation(OfflineLocation offlineLocation) {
        offlineLocationService.delete(offlineLocation);
        updateList();
    }

    private void closeDialog(OfflineLocationForm.CloseEvent event) {
        dialog.close();
    }

    private void editOfflineLocation(OfflineLocation offlineLocation) {
        if (offlineLocation == null) {
            dialog.close();
        } else {
            offlineLocationForm.setOfflineLocation(offlineLocation);
            dialog.open();
        }
    }

    private void addNewLocation() {
        editOfflineLocation(new OfflineLocation());
    }

    private void updateList() {
        offlineExamGrid.setItems(offlineLocationService.getAll());
    }

    private void showNotification(String message, NotificationVariant notificationVariant) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(notificationVariant);
    }
}
