package org.raksti.web.views.userExamHistory;

import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.ExamDataService;
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
import java.util.List;
import java.util.Optional;

@PageTitle("Manu diktātu vēsture")
@Route(value = "my-exam-history", layout = MainLayout.class)
@RolesAllowed("USER")
@Uses(Icon.class)
public class UserExamHistoryView extends VerticalLayout {

    private Grid<ExamData> grid = new Grid<>(ExamData.class, false);

    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;

    @Autowired
    public UserExamHistoryView(ExamDataService examDataService, AuthenticatedUser authenticatedUser) {
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;
        getStyle().set("padding-top", "30px");
        //configure grid
        grid.addColumn(ExamData::getExamId).setAutoWidth(true).setHeader("Eksāmena nosaukums");
        grid.addColumn(ExamData::getResult).setAutoWidth(true).setHeader("Rezultāts").setSortable(true);

        List<ExamData> exams = examDataService.get(getEmail());
        grid.setItems(exams);

        setHorizontalComponentAlignment(Alignment.CENTER);
        add(grid);
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
