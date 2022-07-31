package com.example.application.views.newExam;

import com.example.application.data.service.ExamService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Exam")
@Route(value = "create-exam", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ExamsView extends VerticalLayout {

    private TextField link;
    private TextField naming;
    private Button submitBut;
    private NumberField duration;
    private Button goToExam;
    private Button finishExam;

    private final ExamService examService;

    public ExamsView(ExamService examService) {
        this.examService = examService;
        link = new TextField("Saite uz video");
        naming = new TextField("Diktāta nosaukums");
        submitBut = new Button("Izveidot diktātu");
        duration = new NumberField("Diktāta ilgums (st.)");
        goToExam = new Button("Doties uz diktāta lapu");
        finishExam = new Button("Apstāt diktātu");

        duration.setStep(0.5);
        duration.setMin(0.5);
        duration.setMax(5);
        duration.setHasControls(true);

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Sākt diktātu?");
        Button saveButton = new Button("Jā");
        saveButton.addClickListener(e -> {
            createExam();
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Nē", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dialog.getFooter().add(cancelButton, saveButton);

        finishExam.setEnabled(false);
        finishExam.addClickListener(e -> stopExam());

        goToExam.setEnabled(false);
        enableAndDisableButtons();
        goToExam.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("exam/" + returnExamNaming())));

        submitBut.addClickListener(e -> {
            if (validateFields()) {
                dialog.open();
            } else {
                Notification.show("Lūdzu, aizpildiet visus laukus!");
            }
        });

        add(link, naming, duration, submitBut, goToExam, finishExam);
    }

    private String returnExamNaming() {
        Exam exam = examService.getByFinished(false);
        return exam.getNaming();
    }

    private void enableAndDisableButtons() {
        Exam exam = examService.getByFinished(false);
        if (exam != null) {
            goToExam.setEnabled(true);
            finishExam.setEnabled(true);
            submitBut.setEnabled(false);
        }
    }

    private void stopExam() {
        Exam exam = examService.getByFinished(false);
        exam.setFinished(true);
        examService.save(exam);
        goToExam.setEnabled(false);
        finishExam.setEnabled(false);
        submitBut.setEnabled(true);
    }

    private void createExam() {
        String modifiedLink = modifyLink();
        examService.save(naming.getValue(), link.getValue(), modifiedLink, false, duration.getValue());
        goToExam.setEnabled(true);
        finishExam.setEnabled(true);
        submitBut.setEnabled(false);
        Notification.show("Diktāts izveidots").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private String modifyLink() {
        String link = this.link.getValue().replace("watch?v=", "embed/");
        int trig = 0;
        for (int i = 0; i < link.length(); i++) {
            if (link.charAt(i) == '&') {
                trig = i;
            }
        }
        StringBuilder buf = new StringBuilder(link);
        buf.replace(trig, buf.length(),"");
        return buf.toString();
    }

    private boolean validateFields() {
        return !link.getValue().isEmpty() && !naming.getValue().isEmpty() && !duration.isEmpty();
    }
}
