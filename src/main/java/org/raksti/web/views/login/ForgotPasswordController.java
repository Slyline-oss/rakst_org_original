package org.raksti.web.views.login;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.views.registration.EmailAndPasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class ForgotPasswordController {
    public static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

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
                "Ignorējiet šo e-pastu, ja atceraties savu paroli vai arī neesat veikuši šo pieprasījumu. " +
                "https://raksti.org/reset-password/" + link;
        if (!emailAndPasswordValidation.isValidEmailAddress(StringUtils.trim(emailText))) {
            Notification.show("Nepareizi ievadīts e-pasts!").setPosition(Notification.Position.TOP_START);
            logger.info("Unsuccessful attempt to restore non-existent user: " + emailText);
        } else {
            User user = userRepository.findByEmail(emailText);
            if (user == null) {
                showNotification("Nav tāda lietotāja", NotificationVariant.LUMO_ERROR);
            } else {
                user.setResetPasswordToken(link);
                userRepository.save(user);
                emailSenderService.sendEmail(emailText, content, subject);
                showNotification("Vēstule nosūtīta uz e-pastu!", NotificationVariant.LUMO_SUCCESS);
            }
        }
    }

    public void showNotification(@NotNull String text, NotificationVariant notificationVariant) {
        Notification notification = new Notification();
        notification.setText(text);
        notification.addThemeVariants(notificationVariant);
        notification.setDuration(5000);
        notification.setPosition(Notification.Position.TOP_START);
        notification.open();
    }
}
