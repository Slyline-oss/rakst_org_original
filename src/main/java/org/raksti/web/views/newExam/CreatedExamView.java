package org.raksti.web.views.newExam;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.data.value.ValueChangeMode;
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
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Exam")
@Route(value = "exam-current", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class CreatedExamView extends VerticalLayout implements BeforeEnterObserver {

    private final static Logger logger = LoggerFactory.getLogger(CreatedExamView.class);

    private final TextArea textArea;


    private final ExamService examService;
    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;
    private final ResultSaver resultSaver;

    public CreatedExamView(ExamService examService, ExamDataService examDataService, AuthenticatedUser authenticatedUser, ResultSaver resultSaver) {
        this.examService = examService;
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;
        this.resultSaver = resultSaver;
        textArea = new TextArea();
        getStyle().set("padding-top", "30px");

        addClassNames("created-exam-view");

        Div wrapper = new Div();
        wrapper.addClassNames("exam-wrapper");

        Div text = new Div();
        text.addClassNames("exam-textArea");

        Div frame = new Div();
        frame.addClassNames("exam-video");

        textArea.setWidthFull();
        textArea.setLabel("Teksta ievades lauks");
        text.add(textArea);
        textArea.addClassNames("exam-textArea-element");

        restoreContent();

        Exam exam = examService.getByFinished(false);
        String link = exam.getEmbedLink();
        String naming = exam.getNaming();
        H1 title = new H1(naming);

        Button submitBut = new Button("Iesniegt");
        submitBut.addClassNames("created-exam-view-butt");
        text.add(submitBut, new Paragraph("Diktātu būs iespējams iesniegt norises nobeigumā"),
                new Paragraph("Sadaļa Oriģinālteksts. Diktāta oriģinālteksts un skaidrojumi būs pieejami uzreiz pēc diktāta nobeiguma."));

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Iesniegt diktātu?");
        Button saveButton = new Button("Jā");
        saveButton.addClickListener(e -> {
            saveContent();
            dialog.close();
            if (checkProfileFilled()) {
                getUI().ifPresent(ui -> ui.navigate("profile"));
            } else {
                getUI().ifPresent(ui -> ui.navigate("about"));
            }
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
        frame.add(iFrame);
        iFrame.setAllow("autoplay;picture-in-picture;xr-spatial-tracking;encrypted-media");

        wrapper.add(frame, text);

        add(title, wrapper);

        setHorizontalComponentAlignment(Alignment.CENTER);

        submitBut.addClickListener(e -> {
            if (checkIfAvailableToFinish()) dialog.open();
            else Notification.show("Pagaidām nevar iesniegt diktātu!", 7000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
        });



        textArea.setValueChangeTimeout(60000);
        textArea.setValueChangeMode(ValueChangeMode.TIMEOUT);
        textArea.addValueChangeListener(e -> {
           autoSaveContent();
        });

    }

    private boolean checkProfileFilled() {
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getAge() == null || user.getCity() == null ||
                    user.getTelNumber() == null || user.getBirthday() == null ||
                    user.getEducation() == null || user.getCountry() == null;
        }
        return false;
    }

    private boolean checkIfAvailableToFinish() {
        Exam exam = examService.getByFinished(false);
        return exam.isAllowToWrite();
    }


    private void restoreContent() {
        Optional<User> maybeUser = authenticatedUser.get();
        Exam exam = examService.getByFinished(false);
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            ExamData examData = examDataService.get(user.getEmail(), exam.getId());
            if (examData != null) {
                textArea.setValue(examData.getTextData() == null ? "" : examData.getTextData());
            }
        }
    }



    private void autoSaveContent() {
        Optional<User> maybeUser = authenticatedUser.get();
        Exam exam = examService.getByFinished(false);
        if (exam == null) {
            getUI().ifPresent(ui -> ui.navigate("about"));
            return;
        }
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            ExamData examData = examDataService.get(user.getEmail(), exam.getId());
            if (examData == null) {
                examDataService.save(user.getEmail(), textArea.getValue(), exam.getId());
            } else {
               examData.setTextData(textArea.getValue());
               examDataService.save(examData);
            }
        }
    }

    private void saveContent() {
        Optional<User> maybeUser = authenticatedUser.get();
        Exam exam = examService.getByFinished(false);
        if (exam == null) {
            getUI().ifPresent(ui -> ui.navigate("about"));
            return;
        }
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            ExamData examData = examDataService.get(user.getEmail(), exam.getId());
            examData.setFinished(true);
            examData.setTextData(textArea.getValue());
            examDataService.save(examData);
            logger.info("Results saved in DB");
            resultSaver.saveResultIntoFile(user.getEmail(),textArea.getValue());
        } else logger.warn("Error saving results in DB");

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
