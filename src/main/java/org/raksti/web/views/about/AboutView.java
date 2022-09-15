package org.raksti.web.views.about;

import org.raksti.web.data.Role;
import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.ExamDataService;
import org.raksti.web.data.service.ExamService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.newExam.Exam;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.Optional;


@PageTitle("Sākumlapa")
@Route(value = "/", layout = MainLayout.class)
@RouteAlias(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout implements BeforeEnterObserver {


    private Paragraph paragraph;

    private final ExamService examService;
    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;

    public AboutView(AboutViewService aboutViewService, ExamService examService, ExamDataService examDataService, AuthenticatedUser authenticatedUser) {
        this.examService = examService;
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;

        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("This place intentionally left empty"));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        paragraph = new Paragraph(aboutViewService.getText());
        add(paragraph);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    public Paragraph getParagraph() {
        return paragraph;
    }

    public void setParagraphValue(String text) {
        this.paragraph.setText(text);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Exam exam = examService.getByFinished(false);
        if (exam != null && !exam.isFinished() && exam.isAllowToShow()) {
            Optional<User> maybeUser = authenticatedUser.get();
            if (maybeUser.isPresent()) {
                User user = maybeUser.get();
                ExamData examData = examDataService.get(user.getEmail(), exam.getId());
                if (examData == null && user.getRoles().contains(Enum.valueOf(Role.class, "USER"))) {
                    beforeEnterEvent.forwardTo("exam-current");
                }
            }
        }
    }
}