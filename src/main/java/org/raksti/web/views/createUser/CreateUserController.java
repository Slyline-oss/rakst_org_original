package org.raksti.web.views.createUser;

import com.vaadin.flow.component.notification.NotificationVariant;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.security.UserDetailsServiceImpl;
import org.raksti.web.views.registration.EmailAndPasswordValidator;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@Controller
public class CreateUserController {

    private final EmailSenderService emailSenderService;
    private final EmailAndPasswordValidator emailAndPasswordValidation;
    private final UserDetailsServiceImpl userService;

    public CreateUserController(EmailSenderService emailSenderService, EmailAndPasswordValidator emailAndPasswordValidation, UserDetailsServiceImpl userService) {
        this.emailSenderService = emailSenderService;
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.userService = userService;
    }

    public void sendEmailAdmin(EmailField email) {
        String emailText = email.getValue();
        String password = UUID.randomUUID().toString();
        String subject = "Raksti.org - administratora tiesības";
        String content = "Sveiki! Jums ir pievienotās administratora tiesības. " +
                "Jūsu pagaidu parole ir: " + password + ". " +
                "Lūdzu, pamainiet paroli pēc pieslēgšanas." +
                "Paldies!";
        if (!emailAndPasswordValidation.isValidEmailAddress(emailText)) {
            Notification.show("Nepareizi ievadīts e-pasts!");
        } else if (userService.findUserByEmail(emailText) != null) {
            Notification.show("Tāds lietotājs jau pastāv!");
        } else {
            emailSenderService.sendEmail(emailText, content, subject);
            userService.registerAdmin(emailText.toLowerCase(Locale.ROOT), password);
            Notification.show("Admins izveidots", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }

    public void sendEmailUser(EmailField email, String firstName, String lastName, LocalDate birthday, String telNumber, String language,
                              String country, String city, String age, String education, String gender) {
        String emailText = email.getValue();
        String password = UUID.randomUUID().toString();
        String subject = "Raksti.org - Lietotāja profils";
        String content = "Sveiki! Jums ir izveidots Raksti.org lietotāja profils. " +
                "Jūsu pagaidu parole ir: " + password + " . " +
                "Lūdzu, pamainiet paroli pēc pieslēgšanas." +
                "Paldies!";
        if (!emailAndPasswordValidation.isValidEmailAddress(emailText)) {
            Notification.show("Nepareizi ievadīts e-pasts!");
        } else if (userService.findUserByEmail(emailText) != null) {
            Notification.show("Tāds lietotājs jau pastāv!");
        } else {
            emailSenderService.sendEmail(emailText, content, subject);
            userService.register(emailText.toLowerCase(Locale.ROOT), firstName, lastName, password, birthday, telNumber, language, country, city, age, education, gender, true);
            Notification.show("Lietotājs izveidots", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }
}
