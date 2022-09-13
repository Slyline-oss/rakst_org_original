package org.raksti.web.views.login;

import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.security.UserDetailsServiceImpl;
import org.raksti.web.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ForgotPasswordController {

    @Autowired
    private final EmailAndPasswordValidation emailAndPasswordValidation;
    private final UserRepository userRepository;
    @Autowired
    private final EmailSenderService emailSenderService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public ForgotPasswordController(EmailAndPasswordValidation emailAndPasswordValidation, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public void sendEmail(EmailField email) {
        String emailText = email.getValue();
        String subject = "Raksti.org - paroles atjaunošana";
        String link = UUID.randomUUID().toString();
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" +"http://localhost:8080/reset-password/" + link + "\">Change my password</a></p>"
                + "<br>"
                + "Ignore this email if you do remember your password, "
                + "or you have not made the request.";
        if (!emailAndPasswordValidation.validateEmail(emailText)) {
            Notification.show("Nepareizi ievadīts e-pasts!");
        } else {
            User user = userRepository.findByEmail(emailText);
            user.setResetPasswordToken(link);
            userRepository.save(user);
            emailSenderService.sendEmail(emailText, content,
                    subject);
            Notification.show("Vēstule nosūtīta uz e-pastu!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }
}
