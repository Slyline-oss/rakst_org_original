package org.raksti.web.views.participateInExam;

import org.raksti.web.data.service.ExamDataService;
import org.raksti.web.data.service.ExamService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.newExam.Exam;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Piedalīties diktātā tiešsaistē")
@Route(value = "participate-in-exam", layout = MainLayout.class)
@RolesAllowed("USER")
public class ParticipateInExamView extends VerticalLayout {

    private final ExamService examService;

    public ParticipateInExamView(ExamService examService) {
        this.examService = examService;

        H3 title = new H3("Diktāta pieejamība: ");
        Button goToExam = new Button("Sākt diktātu");
        goToExam.setEnabled(false);
        enableButton(goToExam);
        goToExam.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("exam-current")));

        add(title, goToExam);

    }

    private void enableButton(Button button) {
        Exam exam = examService.getByFinished(false);
        if (exam != null && exam.isAllowToShow()) {
            button.setEnabled(true);
        }
    }
}
