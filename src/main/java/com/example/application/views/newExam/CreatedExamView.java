package com.example.application.views.newExam;

import com.example.application.data.service.ExamService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Exam")
@Route("exam/:naming/")
@RolesAllowed("USER")
public class CreatedExamView extends VerticalLayout implements BeforeEnterObserver {

    private String naming;
    private TextArea textArea;

    private final ExamService examService;

    public CreatedExamView(ExamService examService) {
        this.examService = examService;
        textArea = new TextArea();

        textArea.setWidthFull();
        textArea.setLabel("Rakstiet šeit diktātu");

        Exam exam = examService.getByFinished(false);
        String link = exam.getEmbedLink();
        String naming = exam.getNaming();
        H1 title = new H1(naming);

        Button submitBut = new Button("Iesniegt");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Iesniegt diktātu?");
        Button saveButton = new Button("Jā");
        saveButton.addClickListener(e -> {
            dialog.close();
            getUI().ifPresent(ui -> ui.navigate("about"));
            Notification notification = new Notification("Paldies par dalību latviešu diktātā!");
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Nē", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dialog.getFooter().add(cancelButton, saveButton);


        IFrame iFrame = new IFrame();
        iFrame.setSrc(link);
        iFrame.setWidth("50%");
        iFrame.setHeight("500px");

        add(title, iFrame, textArea, submitBut);

        submitBut.addClickListener(e -> dialog.open());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        naming = beforeEnterEvent.getRouteParameters().get("naming").get();
        Exam exam = examService.get(naming);
        if (exam == null || exam.isFinished()) {
            beforeEnterEvent.forwardTo("about");
        }
    }
}
