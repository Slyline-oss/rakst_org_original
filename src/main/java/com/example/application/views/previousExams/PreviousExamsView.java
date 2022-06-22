package com.example.application.views.previousExams;

import com.example.application.data.person.Person;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle("Previous Exams")
@Route(value = "previous-exams", layout = MainLayout.class)
@AnonymousAllowed
public class PreviousExamsView extends VerticalLayout {

    public PreviousExamsView() {

        Grid<Person> grid = new Grid<>(Person.class, false);
        grid.addColumn(Person::getFirstName).setHeader("First name");
        grid.addColumn(Person::getLastName).setHeader("Last name");
        grid.addColumn(Person::getEmail).setHeader("Email");

        List<Person> people = Arrays.asList(new Person("Artjoms", "Melihovs", "adf@email.com", 0),
                new Person("Mike", "Wazowscki", "mike@email.com", 1));
        grid.setItems(people);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        add(grid);
    }
}
