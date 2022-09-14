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
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Offline Locations Admin")
@Route(value = "offline-locations-admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OfflineLocationAdminView extends VerticalLayout {
    private final OfflineLocationService offlineLocationService;
    private final Grid<OfflineLocation> offlineExamGrid = new Grid<>(OfflineLocation.class);
    private final Dialog dialog = new Dialog();

    public OfflineLocationAdminView(OfflineLocationService offlineLocationService) {
        this.offlineLocationService = offlineLocationService;

        dialog.add(createDialogLayout());

        Button button = new Button("Add Location", e -> dialog.open());

        getData();
        add(dialog, button, offlineExamGrid);
    }

    private void getData() {
        offlineExamGrid.removeAllColumns();
        offlineExamGrid.addColumn(OfflineLocation::getCity).setHeader("City");
        offlineExamGrid.addColumn(OfflineLocation::getAddress).setHeader("Address");
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTotal).setHeader("Slots Total");
        offlineExamGrid.addColumn(OfflineLocation::getSlotsTaken).setHeader("Slots Taken");

        offlineExamGrid.setItems(offlineLocationService.getAll());

    }

    private VerticalLayout createDialogLayout() {
        dialog.setHeaderTitle("new Location Details");

        Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> dialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(closeButton);

        TextField countryField = new TextField("Country");
        TextField cityField = new TextField("City");
        TextField addressField = new TextField("Address");
        IntegerField totalSlots = new IntegerField("Total Slots");
        totalSlots.setHasControls(true);
        totalSlots.setMin(1);

        VerticalLayout fieldLayout = new VerticalLayout(countryField, cityField, addressField, totalSlots);

        Button addLocation = new Button("Add");
        addLocation.addClickListener(buttonClickEvent -> {
            if (countryField.isEmpty() || cityField.isEmpty() || addressField.isEmpty() || totalSlots.isEmpty()) {
                showNotification("Fill in all fields!", NotificationVariant.LUMO_ERROR);
            } else {
                if (offlineLocationService.getAllByCountyAndByCityAndByAddress(countryField.getValue(), cityField.getValue(), addressField.getValue()).size() > 0) {
                    showNotification("this location already exist", NotificationVariant.LUMO_ERROR);
                } else {
                    offlineLocationService.save(new OfflineLocation(countryField.getValue(), cityField.getValue(), addressField.getValue(), totalSlots.getValue()));
                    dialog.close();
                    getData();
                }
            }
        });
        Button cancel = new Button("cancel");
        cancel.addClickListener(buttonClickEvent -> dialog.close());

        HorizontalLayout buttonsLayout = new HorizontalLayout(addLocation, cancel);

        VerticalLayout dialogLayout = new VerticalLayout(fieldLayout, buttonsLayout);

        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        fieldLayout.getStyle().set("width", "300px").set("max-width", "100%");

        return dialogLayout;
    }

    private void showNotification(String message, NotificationVariant notificationVariant) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(notificationVariant);
    }
}
