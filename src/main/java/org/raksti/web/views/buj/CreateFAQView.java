package org.raksti.web.views.buj;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.FAQ;
import org.raksti.web.data.service.FAQService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@PageTitle("BUJ - izveidot")
@Route(value = "buj-create", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateFAQView extends VerticalLayout {

    private final Select<String> faqSelect = new Select<>();
    private final TextArea answer = new TextArea();
    private final TextField question = new TextField();
    private final Button create = new Button("Izveidot jautājumu");
    private final Button delete = new Button("Dzēst");
    Grid<FAQ> grid = new Grid<>(FAQ.class, false);
    private List<FAQ> questionList;
    private List<String> questionTitles = new ArrayList<>();

    private final FAQService faqService;

    public CreateFAQView(FAQService faqService) {
        this.faqService = faqService;
        loadQuestions();
        landing();
    }

    //makes layout of page
    private void landing() {
        //selector
        faqSelect.setEmptySelectionAllowed(true);
        faqSelect.setLabel("Izveidotie jautājumi");
        faqSelect.addValueChangeListener(e -> returnText(e.getValue()));
        //text area with answer
        answer.setLabel("Atbilde uz jautājumu");
        answer.setWidth("400px");
        answer.setClearButtonVisible(true);
        //text field with title of question
        question.setClearButtonVisible(true);
        question.setPlaceholder("Jautājums");
        question.setLabel("Jauns jautājums");
        question.setMinWidth("400px");
        //Button create
        create.addClickListener(e -> createQuestion());
        //Grid
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(FAQ::getQuestion).setAutoWidth(true).setHeader("Jautājums");
        grid.setItems(questionList);
        //Button delete
        delete.addClickListener(e -> deleteQuestion());
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        add(faqSelect, question, answer, create, grid, delete);
    }

    //deletes question
    private void deleteQuestion() {
        Set<FAQ> list = grid.getSelectedItems();
        if (!list.isEmpty()) {
            faqService.deleteItems(list);
            loadQuestions();
            grid.setItems(questionList);
            Notification.show("Jautājums dzēsts!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else Notification.show("Izvēlēties, ko dzēst", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
    }

    //sets answer and question data of chosen value in fields
    private void returnText(String value) {
        for (FAQ faq : questionList) {
            if (Objects.equals(value, faq.getQuestion())) {
                question.setValue(value);
                answer.setValue(faq.getAnswer());
            }
        }
        if (value == null) {
            question.setValue("");
            answer.setValue("");
        }
    }

    //validate fields
    private boolean validate() {
        if (question.getValue().isEmpty() || answer.getValue().isEmpty()) {
            Notification.show("Aizpildiet lūdzu visus laukus!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        } else if (faqService.getByQuestion(question.getValue()) != null) {
            Notification.show("Tāds jautājums jau pastāv", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
            return false;
        }
        return true;
    }

    //creates question in writes it in db
    private void createQuestion() {
        if (validate()) {
            faqService.save(new FAQ(question.getValue(), answer.getValue()));
            loadQuestions();
            grid.setItems(questionList);
            Notification.show("Jautājums izveidots!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }

    //sets created question in selector
    private void loadQuestionTitles() {
        for (FAQ faq : questionList) {
            questionTitles.add(faq.getQuestion());
        }
    }

    //updates question list and titles
    private void loadQuestions() {
        questionList = faqService.getAllFAQ();
        questionTitles.clear();
        loadQuestionTitles();
        faqSelect.setItems(questionTitles);
    }

}
