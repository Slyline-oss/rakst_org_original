package com.example.application.views.login;

import com.example.application.data.service.UserRepository;
import com.example.application.emailSender.EmailSenderService;
import com.example.application.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Forgot Password")
@Route(value = "restore-password")
@AnonymousAllowed
public class ForgotPassword extends VerticalLayout {

    private Button submit;
    private EmailField email;

    @Autowired
    private final EmailAndPasswordValidation emailAndPasswordValidation;
    private final UserRepository userRepository;
    @Autowired
    private final EmailSenderService emailSenderService;

    public ForgotPassword(EmailAndPasswordValidation emailAndPasswordValidation, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;

        email = new EmailField();
        submit = new Button();

        email.setLabel("Ievadiet e-pastu");
        email.setPlaceholder("example@email.com");
        email.setErrorMessage("Lūdzu, ievadiet pareizo e-pastu");
        email.setClearButtonVisible(true);
        email.setPattern("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        submit.setText("nosūtīt");
        submit.addClickListener(e -> sendEmail());

        add(email,submit);
    }

    private void sendEmail() {
        String emailText = email.getValue();
        if (!emailAndPasswordValidation.validateEmail(emailText)) {
            Notification.show("Nepareizi ievadīts e-pasts!");
        } else if (userRepository.findByEmail(emailText) == null) {
            Notification.show("Nav tāda lietotāja!");
        } else {
            emailSenderService.sendEmail(emailText, "Lai uzstadītu jauno paroli noklikšķiniet uz linku: ...",
                    "Raksti.org - paroles atjaunošana");
            Notification.show("Vēstule nosūtīta uz e-pastu!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }

}
