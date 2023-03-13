package org.raksti.web.views.userExamHistory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import org.apache.commons.lang3.StringUtils;
import org.raksti.web.certificateCreator.DownloadFile;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@PageTitle("Manu diktātu vēsture")
@Route(value = "my-exam-history", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class UserExamHistoryView extends VerticalLayout {

    private final AuthenticatedUser authenticatedUser;
    private final ResultService resultService;

    private final DownloadFile downloadFile;

    @Autowired
    public UserExamHistoryView(ExamDataService examDataService, ExamService examService, AuthenticatedUser authenticatedUser, ResultService resultService, DownloadFile downloadFile) throws IOException {
        this.authenticatedUser = authenticatedUser;
        this.resultService = resultService;
        this.downloadFile = downloadFile;

        getStyle().set("padding-top", "30px");
        //configure grid
        Grid<Exam> grid = new Grid<>(Exam.class, false);
        grid.addColumn(Exam::getNaming).setAutoWidth(true).setHeader("Diktāta nosaukums");

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

    private void generateExamButton() throws IOException {
        Button showResults = new Button();
        showResults.setText("Apskatīt rezultātus");

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            String email = user.getEmail();
            String firstName = StringUtils.capitalize(user.getFirstName());
            String lastName = StringUtils.capitalize(user.getLastName());
            String fullName = user.getFirstName().equalsIgnoreCase("Anonims") ? email : firstName + " " + lastName;

            if (resultService.findByEmail(email).isPresent()) {
                Result result = resultService.findByEmail(email).get();
                String resultId = result.getResultId();
                String url = "https://raksti.org/results/labots?id=" + resultId;
                showResults.addClickListener(e -> {
                    UI.getCurrent().getPage().executeJs("window.open(\"" + url + "\", \"_self\");");
                });

                Anchor link = downloadFile.getLink(fullName, user.getId());
                link.setText("Lejupielādēt apliecinājumu");
                link.getElement().setAttribute("download", true);

                add(link);
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
