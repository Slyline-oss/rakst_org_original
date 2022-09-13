package org.raksti.web.views.offlineExam;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.OfflineExam;
import org.raksti.web.data.service.OfflineExamService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;

@PageTitle("Offline Exams Participants")
@Route(value = "create-new-offline-exam", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateNewExamForm extends VerticalLayout {
    private final OfflineExamService offlineExamService;

    public CreateNewExamForm(OfflineExamService offlineExamService) {
        this.offlineExamService = offlineExamService;
        FormLayout formLayout = new FormLayout();

        TextField city = new TextField();

        TextField address = new TextField();

        IntegerField totalSlots = new IntegerField();
        totalSlots.setHasControls(true);
        totalSlots.setMin(1);

        Button createNewOfflineExam = new Button("Create New Offline Exam");
        createNewOfflineExam.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        createNewOfflineExam.addClickListener(buttonClickEvent -> {
            if (!city.isEmpty() && !address.isEmpty() && !totalSlots.isEmpty()) {
                this.offlineExamService.save(new OfflineExam(city.getValue(), address.getValue(), totalSlots.getValue()));
                city.clear();
                address.clear();
                totalSlots.clear();
            } else {
                Notification notification = Notification.show("Fill in all fields!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        formLayout.addFormItem(city, "City");
        formLayout.addFormItem(address, "Address");
        formLayout.addFormItem(totalSlots, "Total Slots");
        formLayout.add(new HorizontalLayout(createNewOfflineExam));
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1)
        );

        add(formLayout);
    }
}
