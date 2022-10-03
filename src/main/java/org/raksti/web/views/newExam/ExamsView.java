package org.raksti.web.views.newExam;

import org.raksti.web.data.service.ExamService;
import org.raksti.web.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Diktāts")
@Route(value = "create-exam", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ExamsView extends VerticalLayout {

    private final TextField link;
    private final TextField naming;
    private final Button submitBut;
    private final Button goToExam;
    private final Button finishExam;
    private final Button allowToShow;
    private final Button allowToWrite;


    private final ExamService examService;

    public ExamsView(ExamService examService) {
        this.examService = examService;
        link = new TextField("Saite uz video");
        naming = new TextField("Diktāta nosaukums");
        submitBut = new Button("Izveidot diktātu", new Icon(VaadinIcon.CHECK));
        goToExam = new Button("Doties uz diktāta lapu");
        finishExam = new Button("Apstādināt diktātu");
        allowToShow = new Button("Uzsākt diktātu", new Icon(VaadinIcon.CHECK));
        allowToWrite = new Button("Ļaut iesniegt diktātu", new Icon(VaadinIcon.CHECK));

        iconStatus();
        fillFields();

        //confirmation dialog
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Sākt diktātu?");
        Button saveButton = new Button("Jā");
        saveButton.addClickListener(e -> {
            createExam();
            submitBut.getIcon().setVisible(true);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Nē", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dialog.getFooter().add(cancelButton, saveButton);

        allowToWrite.setEnabled(false);
        allowToWrite.addClickListener(e -> enableToFinishExam());

        allowToShow.setEnabled(false);
        allowToShow.addClickListener(e -> showExamForParticipants());

        finishExam.setEnabled(false);
        finishExam.addClickListener(e -> stopExam());

        goToExam.setEnabled(false);
        enableAndDisableButtons();
        goToExam.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("exam-current")));

        submitBut.addClickListener(e -> {
            if (validateFields()) {
                dialog.open();
            } else {
                showNotification("Lūdzu, aizpildiet visus laukus!");
            }
        });

        add(link, naming, goToExam);

        HorizontalLayout hr = new HorizontalLayout();
        VerticalLayout vl1 = new VerticalLayout();
        vl1.add(submitBut, allowToShow, allowToWrite, finishExam);
        hr.add(vl1);
        hr.setPadding(true);
        add(hr);
    }

    private void iconStatus() {
        Exam exam = examService.getByFinished(false);
        if (exam != null) {
            submitBut.getIcon().setVisible(true);
            allowToWrite.getIcon().setVisible(exam.isAllowToWrite());
            allowToShow.getIcon().setVisible(exam.isAllowToShow());
        }
        else {
            submitBut.getIcon().setVisible(false);
            allowToShow.getIcon().setVisible(false);
            allowToWrite.getIcon().setVisible(false);
        }
    }

    private void showExamForParticipants() {
        Exam exam = examService.getByFinished(false);
        exam.setAllowToShow(true);
        allowToShow.getIcon().setVisible(true);
        examService.save(exam);
        showNotification("Dalībnieki var pievienoties!");
    }

    private void enableToFinishExam() {
        Exam exam = examService.getByFinished(false);
        exam.setAllowToWrite(true);
        allowToWrite.getIcon().setVisible(true);
        examService.save(exam);
        showNotification("Atļauts rakstīt diktātu!");
    }


    private void enableAndDisableButtons() {
        Exam exam = examService.getByFinished(false);
        if (exam != null) {
            goToExam.setEnabled(true);
            finishExam.setEnabled(true);
            submitBut.setEnabled(false);
            allowToShow.setEnabled(true);
            allowToWrite.setEnabled(true);
            iconStatus();
        }
    }

    private void stopExam() {
        Exam exam = examService.getByFinished(false);
        exam.setFinished(true);
        examService.save(exam);
        goToExam.setEnabled(false);
        finishExam.setEnabled(false);
        submitBut.setEnabled(true);
        allowToShow.setEnabled(false);
        allowToWrite.setEnabled(false);
        turnOffIconsAll();
        showNotification("Diktāts pabeigts");
        naming.setValue("");
        link.setValue("");
    }


    private void fillFields() {
        Exam exam = examService.getByFinished(false);
        if (exam != null) {
            naming.setValue(exam.getNaming());
            link.setValue(exam.getLink());
        }
    }


    private void createExam() {
        String modifiedLink = modifyLink();
        examService.save(naming.getValue(), link.getValue(), modifiedLink, false, false, false);
        goToExam.setEnabled(true);
        finishExam.setEnabled(true);
        submitBut.setEnabled(false);
        allowToShow.setEnabled(true);
        allowToWrite.setEnabled(true);
        turnOffIcons();
        showNotification("Diktāts izveidots!");
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
        return !link.getValue().isEmpty() && !naming.getValue().isEmpty();
    }

    private void turnOffIconsAll() {
        submitBut.getIcon().setVisible(false);
        allowToShow.getIcon().setVisible(false);
        allowToWrite.getIcon().setVisible(false);
    }

    private void turnOffIcons() {
        allowToShow.getIcon().setVisible(false);
        allowToWrite.getIcon().setVisible(false);
    }

    private void showNotification(String text) {
        Notification notification = new Notification(text);
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(7000);
        notification.open();
    }
}
