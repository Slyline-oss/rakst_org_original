package org.raksti.web.views.userExamHistory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.entity.Result;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.ExamDataService;
import org.raksti.web.data.service.ExamService;
import org.raksti.web.data.service.ResultService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.views.newExam.Exam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Manu diktātu vēsture")
@Route(value = "my-exam-history", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class UserExamHistoryView extends VerticalLayout {

    private Grid<Exam> grid = new Grid<>(Exam.class, false);

    private final ExamService examService;

    private final AuthenticatedUser authenticatedUser;
    private final ResultService resultService;

    @Autowired
    public UserExamHistoryView(ExamDataService examDataService, ExamService examService, AuthenticatedUser authenticatedUser, ResultService resultService) {
        this.examService = examService;
        this.authenticatedUser = authenticatedUser;
        this.resultService = resultService;

        getStyle().set("padding-top", "30px");
        //configure grid
        grid.addColumn(Exam::getNaming).setAutoWidth(true).setHeader("Eksāmena nosaukums");

        List<ExamData> examsData = examDataService.get(getEmail());
        List<Exam> exams = new ArrayList<>();
        for (ExamData examData: examsData) {
            Optional<Exam> examOptional = examService.getById(examData.getId());
            examOptional.ifPresent(exams::add);
        }

        grid.setItems(exams);

        setHorizontalComponentAlignment(Alignment.CENTER);
        add(grid);

        generateExamButton();
    }

    private void generateExamButton() {
        Button showResults = new Button();
        showResults.setText("Apskatīt rezultātus");

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            String email = user.getEmail();

            if (resultService.findByEmail(email).isPresent()) {
                Result result = resultService.findByEmail(email).get();
                String resultId = result.getResultId();
                String url = "https://raksti.org/results/labots?id=" + resultId;
                showResults.addClickListener(e -> {
                    UI.getCurrent().getPage().executeJs("window.open(\"" + url + "\", \"_self\");");
                });
            } else {
                showResults.setEnabled(false);
            }

            add(showResults);
        }
    }

    private String getEmail() {
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getEmail();
        }
        return "";
    }

}
