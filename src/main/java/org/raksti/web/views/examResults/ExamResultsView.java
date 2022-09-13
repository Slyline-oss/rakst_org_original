package org.raksti.web.views.examResults;

import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.service.ExamDataService;
import org.raksti.web.views.MainLayout;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@PageTitle("Diktātu rezultāti")
@Route(value = "exam-results", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ExamResultsView extends VerticalLayout {

    private Grid<ExamData> grid = new Grid<>(ExamData.class, false);

    private final ExamDataService examDataService;

    @Autowired
    public ExamResultsView(ExamDataService examDataService) {
        this.examDataService = examDataService;

        Grid.Column<ExamData> emailColumn = grid.addColumn(ExamData::getEmail).setAutoWidth(true).setSortable(true);
        Grid.Column<ExamData> namingColumn = grid.addColumn(ExamData::getExamId).setAutoWidth(true).setSortable(true);

        List<ExamData> examResults = examDataService.get(true);
        GridListDataView<ExamData> dataView = grid.setItems(examResults);
        ExamDataFilter examDataFilter = new ExamDataFilter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(namingColumn).setComponent(
                createFilterHeader("Exam name", examDataFilter::setNaming));
        headerRow.getCell(emailColumn).setComponent(
                createFilterHeader("Email", examDataFilter::setEmail));

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


    private static class ExamDataFilter {
        private final GridListDataView<ExamData> dataView;

        private String email;
        private String naming;

        private ExamDataFilter(GridListDataView<ExamData> dataView) {
            this.dataView = dataView;
            this.dataView.addFilter(this::test);
        }

        public void setEmail(String email) {
            this.email = email;
            this.dataView.refreshAll();
        }

        public void setNaming(String naming) {
            this.naming = naming;
            this.dataView.refreshAll();
        }

        public boolean test(ExamData examData) {
            boolean matchesEmail = matches(examData.getEmail(), email);
            boolean matchesNaming = matches(String.valueOf(examData.getExamId()), naming);

            return matchesEmail && matchesNaming;
        }

        private boolean matches(String value, String searchTerm) {
            return searchTerm == null || searchTerm.isEmpty() || value.toLowerCase(Locale.ROOT).
                    contains(searchTerm.toLowerCase(Locale.ROOT));
        }

    }
}
