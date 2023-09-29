package org.raksti.web.views.sendEmailsPage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.registration.EmailAndPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Sūtīt vēstules")
@Route(value = "email-sender", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EmailSenderView extends VerticalLayout {
    private final static Logger logger = LoggerFactory.getLogger(EmailSenderView.class);

    private final EmailSenderService emailSenderService;
    private final EmailAndPasswordValidator validator;

    private final TextArea textArea = new TextArea();
    private final Button send = new Button("Sūtīt e-pastu");
    private final TextField subject = new TextField("Virsraksts");

    @Autowired
    public EmailSenderView(@NotNull EmailSenderService emailSenderService,
                           @NotNull UserRepository userRepository,
                           @NotNull EmailAndPasswordValidator validator) {

        this.emailSenderService = emailSenderService;
        this.validator = validator;

        makeLayout();

        send.addClickListener(e -> sendEmail(userRepository.findByAllowEmailsTrue()));
    }

    private void makeLayout() {
        getStyle().set("padding-top", "30px");

        subject.setPlaceholder("Vēstules virsraksts");
        subject.setClearButtonVisible(true);
        subject.isRequired();

        textArea.setMinWidth("400px");
        textArea.setMinHeight("300px");

        send.setIcon(new Icon(VaadinIcon.CHECK_CIRCLE));

        add(subject, textArea, send);
    }

    private void sendEmail(List<User> users) {
        logger.info("Sending e-mail to " + users.size() + " users");
        int count = 0;
        for (User user: users) {
            String email = user.getEmail();
            if (validator.validateEmail(email)) {
                try {
                    emailSenderService.sendEmail(email, textArea.getValue(), subject.getValue());
                    count++;
                } catch (Exception e) {
                    logger.warn("Failed to send e-mail to " + email + " : " + e.getMessage());
                }
            } else {
                logger.info(email + " is not a valid e-mail address, skipping");
            }
        }
        logger.info("Sent " + count + " e-mails");
        Notification.show("Veiksmīgi nosutīti " + count + " e-pasti");
    }

}
