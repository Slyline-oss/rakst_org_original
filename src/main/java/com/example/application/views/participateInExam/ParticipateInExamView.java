package com.example.application.views.participateInExam;

import com.example.application.data.entity.User;
import com.example.application.data.service.ExamDataService;
import com.example.application.data.service.ExamService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.example.application.views.newExam.Exam;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Participate in Exam")
@Route(value = "participate-in-exam", layout = MainLayout.class)
@RolesAllowed("USER")
public class ParticipateInExamView extends VerticalLayout {

    private final ExamService examService;
    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;

    public ParticipateInExamView(ExamService examService, ExamDataService examDataService, AuthenticatedUser authenticatedUser) {
        this.examService = examService;
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;

        H3 title = new H3("Diktāta pieejamība: ");
        Button goToExam = new Button("Sākt diktātu");
        goToExam.setEnabled(false);
        String naming = enableButton(goToExam);
        goToExam.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("exam/" + naming)));

        add(title, goToExam);

    }

    private String enableButton(Button button) {
        Exam exam = examService.getByFinished(false);
        if (exam != null) {
            button.setEnabled(true);
            return exam.getNaming();
        }

        return "";
    }
}
