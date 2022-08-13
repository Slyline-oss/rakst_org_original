package com.example.application.views.userExamHistory;

import com.example.application.data.entity.ExamData;
import com.example.application.data.entity.User;
import com.example.application.data.service.ExamDataService;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
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

        //configure grid
        grid.addColumn(ExamData::getId).setAutoWidth(true).setHeader("Eksāmena nosaukums");
        grid.addColumn(ExamData::getResult).setAutoWidth(true).setHeader("Resultāts").setSortable(true);

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
