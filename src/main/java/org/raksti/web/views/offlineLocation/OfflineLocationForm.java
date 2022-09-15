package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.raksti.web.data.entity.OfflineLocation;

public class OfflineLocationForm extends FormLayout {
    private OfflineLocation offlineLocation;

    TextField countryField = new TextField("Country");
    TextField cityField = new TextField("City");
    TextField addressField = new TextField("Address");
    IntegerField totalSlots = new IntegerField("Total Slots");
    Binder<OfflineLocation> binder = new BeanValidationBinder<>(OfflineLocation.class);

    Button save = new Button("Save");
    Button close = new Button("Cancel");

    public OfflineLocationForm() {
        binder.bind(countryField, OfflineLocation::getCountry, OfflineLocation::setCountry);
        binder.bind(cityField, OfflineLocation::getCity, OfflineLocation::setCity);
        binder.bind(addressField, OfflineLocation::getAddress, OfflineLocation::setAddress);
        binder.bind(totalSlots, OfflineLocation::getSlotsTotal, OfflineLocation::setSlotsTotal);

        totalSlots.setHasControls(true);
        totalSlots.setMin(1);

        add(countryField, cityField, addressField, totalSlots, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    public void setOfflineLocation(OfflineLocation offlineLocation) {
        this.offlineLocation = offlineLocation;
        binder.readBean(offlineLocation);
    }

    private void validateAndSave() {
        if (countryField.isEmpty() || cityField.isEmpty() || addressField.isEmpty() || totalSlots.isEmpty()) {
            showNotification("All fields must be filled in!", NotificationVariant.LUMO_ERROR);
        } else if (totalSlots.getValue() < offlineLocation.getSlotsTaken()) {
            showNotification("The number of available slots cannot be less than the number of slots already taken! Slots already taken: " + offlineLocation.getSlotsTaken(), NotificationVariant.LUMO_ERROR);
        } else {
            try {
                binder.writeBean(offlineLocation);
                fireEvent(new SaveEvent(this, offlineLocation));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private void showNotification(String message) {
        Notification notification = Notification.show(message);
    }

    private void showNotification(String message, NotificationVariant notificationVariant) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(notificationVariant);
    }

    // Events
    public static abstract class OfflineLocationFormEvent extends ComponentEvent<OfflineLocationForm> {
        private final OfflineLocation offlineLocation;

        protected OfflineLocationFormEvent(OfflineLocationForm source, OfflineLocation offlineLocation) {
            super(source, false);
            this.offlineLocation = offlineLocation;
        }

        public OfflineLocation getOfflineLocation() {
            return offlineLocation;
        }
    }

    public static class SaveEvent extends OfflineLocationFormEvent {
        SaveEvent(OfflineLocationForm source, OfflineLocation offlineLocation) {
            super(source, offlineLocation);
        }
    }

    public static class CloseEvent extends OfflineLocationFormEvent {
        CloseEvent(OfflineLocationForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
