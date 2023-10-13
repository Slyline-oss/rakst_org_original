package org.raksti.web.views.allExams;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import org.raksti.web.data.service.ExamService;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.newExam.Exam;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.function.Consumer;

@PageTitle("Izveidotie diktāti")
@Route(value = "list-of-exams", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreatedExams extends VerticalLayout {

    private Grid<Exam> grid = new Grid<>(Exam.class, false);

    public CreatedExams(ExamService examService) {

        Grid.Column<Exam> idColumn = grid.addColumn(Exam::getId).setAutoWidth(true).setSortable(true);
        Grid.Column<Exam> namingColumn = grid.addColumn(Exam::getNaming).setAutoWidth(true).setSortable(true);
        Grid.Column<Exam> linkColumn = grid.addColumn(Exam::getNewLink).setAutoWidth(true);

        List<Exam> exams = examService.get();
        GridListDataView<Exam> dataView = grid.setItems(exams);
        ExamFilter examFilter = new ExamFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(namingColumn).setComponent(
                createFilterHeader("Diktāta nosaukums", examFilter::setNaming));
        headerRow.getCell(linkColumn).setComponent(
                createFilterHeader("Diktāta saite", examFilter::setLink));
        headerRow.getCell(idColumn).setText("Diktāta id");

        add(grid);

    }

    private static Component createFilterHeader(String labelText,
                                                Consumer<String> filterChangeConsumer) {
        Label label = new Label(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }


    private static class ExamFilter {
        private final GridListDataView<Exam> dataView;

        private String naming;
        private String link;

        private ExamFilter(GridListDataView<Exam> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setNaming(String naming) {
            this.naming = naming;
            this.dataView.refreshAll();
        }

        public void setLink(String link) {
            this.link = link;
            this.dataView.refreshAll();
        }


        public boolean test(Exam exam) {
            boolean matchesNaming = matches(exam.getNaming(), naming);
            boolean matchesLink = matches(exam.getLink(), link);

            return matchesLink && matchesNaming;
        }


        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty() || value
                    .toLowerCase().contains(searchTerm.toLowerCase());
        }
    }
}
