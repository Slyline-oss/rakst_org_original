package org.raksti.web.views.about;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinService;
import org.raksti.web.data.Role;
import org.raksti.web.data.entity.About;
import org.raksti.web.data.entity.ExamData;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.AboutService;
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

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@PageTitle("Sākumlapa")
@Route(value = "/", layout = MainLayout.class)
@RouteAlias(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout implements BeforeEnterObserver {
    private final static Logger logger = LoggerFactory.getLogger(AboutView.class);

    private final ExamService examService;
    private final ExamDataService examDataService;
    private final AuthenticatedUser authenticatedUser;
    private final AboutService aboutService;

    public AboutView(ExamService examService, ExamDataService examDataService, AuthenticatedUser authenticatedUser, AboutService aboutService) {
        this.examService = examService;
        this.examDataService = examDataService;
        this.authenticatedUser = authenticatedUser;
        this.aboutService = aboutService;

        addClassNames("about-view");
        getStyle().set("padding-top", "30px");
        landing();
        dealWithCookie();
    }

    private void landing() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.addClassNames("about-view-content");
        hl.setJustifyContentMode(JustifyContentMode.AROUND);
        hl.setSpacing(true);
        hl.setPadding(true);
        hl.setAlignItems(Alignment.CENTER);

        Main main = new Main();

        HorizontalLayout content = new HorizontalLayout();
        content.addClassNames("content");
        content.getStyle().set("display", "flex");
        content.getStyle().set("flex-direction", "column");
        content.getStyle().set("justify-content", "flex-start");
        content.getStyle().set("color", "#35294c");
        content.getStyle().set("width", "70%");

        Main textContent = new Main();

        textContent.addClassNames("text-content");
        List<About> blocks = aboutService.getAll();

        Image img = new Image("images/img.png", "pic");
        img.addClassNames("image-img");
        Aside aside = new Aside();
        aside.setWidth(30, Unit.PERCENTAGE);
        aside.getStyle().set("align-self", "top");
        aside.getStyle().set("float", "right");
        aside.add(img);

        boolean firstBlock = true;
        for (About block : blocks) {
            Paragraph header = new Paragraph(firstBlock ? block.getTitle() : block.getTitle() + " (...)");
            header.addClassName("about-block-header");
            textContent.add(header);
            String[] lines = block.split();

            for (String line : lines) {
                Paragraph p = new Paragraph(line);
                p.addClassName("about-block-body");
                textContent.add(p);
                p.setVisible(firstBlock);
                header.addClickListener(e -> p.setVisible(!p.isVisible()));
            }
            firstBlock = false;
        }

        main.add(textContent);
        content.add(main);

        hl.add(content);
        hl.setHeightFull();

        add(hl);
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
                    return;
                }
            }
        }

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            if (checkProfileFilled(user)) {
                beforeEnterEvent.forwardTo("profile");
                logger.info("worked");
            }
        }
    }

    private boolean checkProfileFilled(User user) {
        return user.getAge() == null || user.getCity() == null ||
                user.getTelNumber() == null || user.getBirthday() == null ||
                user.getEducation() == null || user.getCountry() == null;
    }


    private static void dealWithCookie() {
        try {
            Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
            if (cookies != null && cookies.length > 0) {
                boolean cookiePresent = Arrays.stream(cookies).anyMatch(cookie -> "raksti.org".equals(cookie.getName()));
                if (!cookiePresent) {
                    createNotification();
                }
            }
        } catch (Exception e) {
            logger.error("Failed cookies operation: " + e.getMessage());
        }
    }

    private static void createNotification() {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        notification.setPosition(Notification.Position.BOTTOM_STRETCH);

        Div statusText = new Div(new Text("Mūsu tīmekļa vietne ievieto sīkdatnes pārlūkprogrammā, ja pārlūkprogrammas iestatījumos šī funkcija ir aktivizēta. " +
                "Mēs izmantojam sīkdatnes, lai nodrošinātu šīs tīmekļa vietnes funkcionalitāti."));

        Button retryButton = new Button("Apstiprināt");
        retryButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        retryButton.getElement().getStyle().set("margin-left",
                "var(--lumo-space-xl)");
        retryButton.addClickListener(event -> {
            Cookie rakstiOrgCookie = new Cookie("raksti.org", "ir");
            rakstiOrgCookie.setMaxAge(60 * 24 * 365); //1 Year in minutes
            rakstiOrgCookie.setPath(VaadinService.getCurrentRequest().getContextPath());
            VaadinService.getCurrentResponse().addCookie(rakstiOrgCookie);
            notification.close();
        });

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(statusText, retryButton,
                closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
}
