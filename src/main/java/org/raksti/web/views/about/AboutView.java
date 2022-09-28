package org.raksti.web.views.about;

import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.ExamDataService;
import org.raksti.web.data.service.ExamService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.newExam.Exam;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;



@PageTitle("Sākumlapa")
@Route(value = "/", layout = MainLayout.class)
@RouteAlias(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout implements BeforeEnterObserver {

    private final static Logger logger = LoggerFactory.getLogger(AboutView.class);


    private Paragraph paragraph;

    private final ExamService examService;
    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;

    public AboutView(AboutViewService aboutViewService, ExamService examService, ExamDataService examDataService, AuthenticatedUser authenticatedUser) {
        this.examService = examService;
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;

        addClassNames("about-view");

        landing();
    }


    private void landing() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setJustifyContentMode(JustifyContentMode.AROUND);
        hl.setSpacing(true);
        hl.setPadding(true);
        hl.setAlignItems(Alignment.CENTER);
        //Div with alphabetical content
        Div content = new Div();
        content.addClassNames("content");
        content.getStyle().set("display", "flex");
        content.getStyle().set("flex-direction", "column");
        content.getStyle().set("justify-content", "flex-start");

        //H1 and paragraph that contains div "text-content"
        H1 title = new H1("Vai šogad notiks Pasaules diktāts latviešu valodā?");
        title.getStyle().set("font-family", "Raksti-DalaFloda,Times,monospace");
        Paragraph text = new Paragraph("Lai Pasaules diktāts latviešu valodā varētu turpināties un šī gada 15. oktobrī atkal priecēt " +
                "vairākus tūkstošus interesentu, " +
                "ir nepieciešama jūsu palīdzība ziedojuma veidā. Esam atvērti arī ieteikumiem" +
                " un sadarbības piedāvājumiem, ko gaidām oficiālajā e-pastā: raksti@raksti.org.");

        //Div "text-content" that contains h1 and paragraph
        Div textContent = new Div();
        textContent.addClassNames("text-content");
        textContent.add(title, text);
        content.add(textContent);

        //Div with image
        Div image = new Div();
        image.addClassNames("image");

        //Image
        Image img = new Image("images/img.png", "main img");
        image.add(img);
        img.getStyle().set("width", "810px");

        hl.add(content, image);

        hl.setHeightFull();

        add(hl);

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
