package org.raksti.web.views.registration;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.StringUtils;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.security.UserDetailsServiceImpl;

import java.util.UUID;

public class RegistrationForm extends FormLayout {

    private final TextField firstName;
    private final TextField lastName;
    private final EmailField email;
    private final PasswordField password;
    private final PasswordField passwordConfirm;
    private final Checkbox anonymous;

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    private final EmailAndPasswordValidator emailAndPasswordValidation;
    private final EmailSenderService emailSenderService;

    public RegistrationForm(UserDetailsServiceImpl userDetailsService, UserRepository userRepository,
                            EmailAndPasswordValidator emailAndPasswordValidation, EmailSenderService emailSenderService) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.emailSenderService = emailSenderService;

        H3 title = new H3("Reģistrācijas forma");
        firstName = new TextField("Vārds");
        lastName = new TextField("Uzvārds");
        email = new EmailField("E-pasts");

        Checkbox allowMarketing = new Checkbox("Piekrītu jaunumu saņemšanai");
        allowMarketing.getStyle().set("margin-top", "10px");

        anonymous = new Checkbox("Reģistrēties anonīmi");
        anonymous.getStyle().set("margin-top", "10px");

        password = new PasswordField("Parole");
        passwordConfirm = new PasswordField("Apstipriniet paroli");

        Span errorMessageField = new Span();

        Button submitButton = new Button("Pievienoties");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(title, firstName, lastName, email, password, passwordConfirm, allowMarketing, anonymous,
                errorMessageField, submitButton);

        setMaxWidth("500px");

        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP)
        );

        setColspan(title,2);
        setColspan(email, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);

        anonymous.addValueChangeListener(e -> {
           if (anonymous.getValue()) {
               firstName.setEnabled(false);
               lastName.setEnabled(false);
               firstName.setValue("");
               lastName.setValue("");
           } else {
               firstName.setEnabled(true);
               lastName.setEnabled(true);
           }
        });

        submitButton.addClickListener(e -> {
            if (anonymous.getValue()) {
                register("Anonims", "Anonims", email.getValue(),
                        password.getValue(), passwordConfirm.getValue(), allowMarketing.getValue());
            } else {
                register(firstName.getValue(), lastName.getValue(), email.getValue(),
                        password.getValue(), passwordConfirm.getValue(), allowMarketing.getValue());
            }
        });
    }

    public void register(String firstName, String lastName, String email, String password1, String password2, boolean allowMarketing) {
        email = StringUtils.lowerCase(email);
        User user = userRepository.findByEmail(email);
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty())  {
            Notification.show("Lūdzu aizpildiet visus laukus!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(password1.isEmpty()) {
            Notification.show("Parole ir tukša", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (!password1.equals(password2)) {
            Notification.show("Paroles nesakrīt!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (user != null) {
            Notification.show("Tāds lietotājs jau pastāv", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (!emailAndPasswordValidation.isValidEmailAddress(email)) {
            Notification.show("Ievadiet pareizo e-pastu", 5000, Notification.Position.TOP_START);
        } else if (!emailAndPasswordValidation.isValidPassword(password1)) {
            Notification.show("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un ciparus",
                    5000, Notification.Position.TOP_START);
        } else {
            String token = UUID.randomUUID().toString();
            userDetailsService.register(firstName, lastName, email, password1, token, allowMarketing);
            Notification notification = new Notification("Reģistrācija izdevās! Lūdzu, apstipriniet e-pastu! Jums ir nosūtīta vēstule uz noradīto e-pastu");
            sendEmail(token, email);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(15000);
            notification.setPosition(Notification.Position.TOP_STRETCH);
            notification.open();
            getUI().ifPresent(ui -> ui.navigate("about"));
        }
    }

    private void sendEmail(String token, String email) {
        String subject = "Raksti.org - e-pasta apstiprināšana";
        String content = "Sveiki! Lai pabeigtu reģistrāciju," +
                " lūdzam apstiprināt jūsu e-pasta adresi, nospiežot saiti" +
                " https://raksti.org/confirm-email/" + token;
        emailSenderService.sendEmail(email, content, subject);
    }

}
