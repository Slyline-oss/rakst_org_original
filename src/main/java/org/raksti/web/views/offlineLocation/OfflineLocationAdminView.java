package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Offline Locations Admin")
@Route(value = "offline-locations-admin", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class OfflineLocationAdminView extends VerticalLayout {
    private final AuthenticatedUser authenticatedUser;
    private final OfflineLocationService offlineLocationService;
    private final Grid<OfflineLocation> offlineExamGrid = new Grid<>(OfflineLocation.class);
    private final Dialog offlineLocationDialog = new Dialog();
    private final Dialog confirmationDialog = new Dialog();
    private final OfflineLocationForm offlineLocationForm = new OfflineLocationForm();

    public OfflineLocationAdminView(AuthenticatedUser authenticatedUser, OfflineLocationService offlineLocationService) {
        this.authenticatedUser = authenticatedUser;
        this.offlineLocationService = offlineLocationService;

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
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTaken).setHeader("Slots Taken").setSortable(true);
        offlineExamGrid.addComponentColumn(offlineLocation -> {
            Button participate = new Button("participate");
            participate.addClickListener(buttonClickEvent -> participate(offlineLocation));
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
            offlineLocationService.delete(offlineLocation);
            confirmationDialog.close();
            updateList();
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

    private void participate(OfflineLocation offlineLocation) {
        OfflineLocation preloadedLocation = offlineLocation;
        OfflineLocation uptodateLocation = offlineLocationService.getById(offlineLocation.getId());
        Notification notification = Notification.show("upd, slots taken: " + uptodateLocation.getSlotsTaken());
    }
}
