package org.raksti.web.views.login;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.views.registration.EmailAndPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ForgotPasswordController {

    private final EmailAndPasswordValidator emailAndPasswordValidation;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ForgotPasswordController(@NotNull EmailAndPasswordValidator emailAndPasswordValidation,
                                    @NotNull UserRepository userRepository,
                                    @NotNull EmailSenderService emailSenderService) {
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public void sendEmail(EmailField email) {
        String emailText = email.getValue();
        String subject = "Raksti.org - paroles atjaunošana";
        String link = UUID.randomUUID().toString();
        String content = "Klikšķiniet uz saites, lai mainītu paroli. " +
                "Ignorējiet šo e-pastu, ja atceraties savu paroli, vai arī neesat veikuši šo pieprasījumu. " +
                "https://raksti.org/reset-password/" + link;
        if (emailAndPasswordValidation.isValidEmailAddress(emailText)) {
            Notification.show("Nepareizi ievadīts e-pasts!").setPosition(Notification.Position.TOP_START);
        } else {
            User user = userRepository.findByEmail(emailText);
            user.setResetPasswordToken(link);
            userRepository.save(user);
            emailSenderService.sendEmail(emailText, content,
                    subject);
            Notification notification = new Notification();
            notification.setText("Vēstule nosūtīta uz e-pastu!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(5000);
            notification.setPosition(Notification.Position.TOP_START);
            notification.open();
        }
    }
}
