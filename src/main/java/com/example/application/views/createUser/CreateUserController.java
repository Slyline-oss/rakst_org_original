package com.example.application.views.createUser;

import com.example.application.emailSender.EmailSenderService;
import com.example.application.security.UserDetailsServiceImpl;
import com.example.application.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CreateUserController {

    private final EmailSenderService emailSenderService;
    private final EmailAndPasswordValidation emailAndPasswordValidation;
    private final UserDetailsServiceImpl userService;

    public CreateUserController(EmailSenderService emailSenderService, EmailAndPasswordValidation emailAndPasswordValidation, UserDetailsServiceImpl userService) {
        this.emailSenderService = emailSenderService;
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.userService = userService;
    }

    public void sendEmail(EmailField email) {
        String emailText = email.getValue();
        String password = UUID.randomUUID().toString();
        String subject = "Raksti.org - administratora tiesības";
        String content = "Sveiki! Jums ir pievienotās administratora tiesības. " +
                "Jūsu pagaidu parole ir: " + password + ". " +
                "Lūdzu, pamainiet paroli pēc pieslēgšanas." +
                "Paldies!";
        if (!emailAndPasswordValidation.validateEmail(emailText)) {
            Notification.show("Nepareizi ievadīts e-pasts!");
        } else if (userService.findUserByEmail(emailText) != null) {
            Notification.show("Tāds lietotājs jau pastāv!");
        } else {
            emailSenderService.sendEmail(emailText, content, subject);
            userService.registerAdmin(emailText, password);
        }
    }
}
