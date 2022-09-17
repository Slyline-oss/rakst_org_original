package org.raksti.web.views.newExam;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutEventListener;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.aspectj.weaver.ast.Not;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.ExamDataService;
import org.raksti.web.data.service.ExamService;
import org.raksti.web.security.AuthenticatedUser;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Exam")
@Route("exam-current")
@RolesAllowed({"USER", "ADMIN"})
public class CreatedExamView extends VerticalLayout implements BeforeEnterObserver {

    private final TextArea textArea;


    private final ExamService examService;
    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;

    public CreatedExamView(ExamService examService, ExamDataService examDataService, AuthenticatedUser authenticatedUser) {
        this.examService = examService;
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;
        textArea = new TextArea();

        textArea.setWidthFull();
        textArea.setLabel("Rakstiet šeit diktātu");

        Exam exam = examService.getByFinished(false);
        String link = exam.getEmbedLink();
        String naming = exam.getNaming();
        H1 title = new H1(naming);

        Button submitBut = new Button("Iesniegt");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Iesniegt diktātu?");
        Button saveButton = new Button("Jā");
        saveButton.addClickListener(e -> {
            saveContent();
            dialog.close();
            getUI().ifPresent(ui -> ui.navigate("about"));
            Notification notification = new Notification("Paldies par dalību latviešu diktātā!");
            notification.setPosition(Notification.Position.TOP_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Nē", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        dialog.getFooter().add(cancelButton, saveButton);


        IFrame iFrame = new IFrame();
        iFrame.setSrc(link);
        iFrame.setWidth("50%");
        iFrame.setHeight("500px");

        add(title, iFrame, textArea, submitBut);

        setHorizontalComponentAlignment(Alignment.CENTER);

        submitBut.addClickListener(e -> {
            if (exam.isAllowToWrite()) dialog.open();
            else Notification.show("Pagaidām nevar iesniegt diktātu!").addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });

//        UI.getCurrent().addShortcutListener(this::autoSaveContent, Key.SPACE).allowBrowserDefault();

        textArea.setValueChangeTimeout(60000);
        textArea.setValueChangeMode(ValueChangeMode.TIMEOUT);
        textArea.addValueChangeListener(e -> {
           autoSaveContent();
        });

    }



    private void autoSaveContent() {
        Optional<User> maybeUser = authenticatedUser.get();
        Exam exam = examService.getByFinished(false);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            ExamData examData = examDataService.get(user.getEmail(), exam.getId());
            if (examData == null) {
                examDataService.save(user.getEmail(), textArea.getValue(), exam.getId());
                Notification.show("Triggered new");
            } else {
               examData.setTextData(textArea.getValue());
               examDataService.save(examData);
               Notification.show("Triggered");
            }
        }
    }

    private void saveContent() {
        Optional<User> maybeUser = authenticatedUser.get();
        Exam exam = examService.getByFinished(false);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            ExamData examData = examDataService.get(user.getEmail(), exam.getId());
            examData.setFinished(true);
            examData.setTextData(textArea.getValue());
            examDataService.save(examData);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Exam exam = examService.getByFinished(false);
        String email = null;
        boolean finished;
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            email = user.getEmail();
            if (user.getRoles().contains(Enum.valueOf(Role.class,"USER")) && !exam.isAllowToShow()) {
                beforeEnterEvent.forwardTo("about");
            }
        }
        if (exam == null || exam.isFinished()) {
            beforeEnterEvent.forwardTo("about");
        }

        ExamData examData = examDataService.get(email, exam.getId());
        if (examData != null && examData.isFinished()) {
            beforeEnterEvent.forwardTo("about");
        }

    }
}
