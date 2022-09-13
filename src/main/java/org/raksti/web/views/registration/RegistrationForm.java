package org.raksti.web.views.registration;

import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.security.UserDetailsServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class RegistrationForm extends FormLayout {

    private H3 title;

    private TextField firstName;
    private TextField lastName;

    private EmailField email;

    private PasswordField password;
    private PasswordField passwordConfirm;

    private Checkbox allowMarketing;

    private Checkbox anonymous;

    private Span errorMessageField;

    private Button submitButton;

    private Button facebookLogin;

    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;
    @Autowired
    private final EmailAndPasswordValidation emailAndPasswordValidation;
    private final EmailSenderService emailSenderService;

    public RegistrationForm(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, EmailAndPasswordValidation emailAndPasswordValidation, EmailSenderService emailSenderService) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        this.emailSenderService = emailSenderService;

        title = new H3("Reģistrācijas forma");
        firstName = new TextField("Vārds");
        lastName = new TextField("Uzvārds");
        email = new EmailField("E-pasts");

        allowMarketing = new Checkbox("Allow Marketing Emails?");
        allowMarketing.getStyle().set("margin-top", "10px");

        anonymous = new Checkbox("Reģistrēties anonimi?");
        anonymous.getStyle().set("margin-top", "10px");

        password = new PasswordField("Parole");
        passwordConfirm = new PasswordField("Apstipriniet paroli");

        errorMessageField = new Span();

        submitButton = new Button("Pievienoties");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        facebookLogin = new Button("Pievienoties ar Facebook");
        facebookLogin.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        add(title, firstName, lastName, email, password, passwordConfirm, allowMarketing, anonymous,
                errorMessageField, submitButton, facebookLogin);

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

        facebookLogin.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("/oauth2/authorization/facebook"));
        });

        submitButton.addClickListener(e -> {
            if (anonymous.getValue()) {
                register(email.getValue(), password.getValue(), passwordConfirm.getValue());
            } else {
                register(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue(), passwordConfirm.getValue());
            }
        });
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }


    public void register(String firstName, String lastName, String email, String password1, String password2) {
        User user = userRepository.findByEmail(email);
        if (firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty())  {
            Notification.show("Aizpildiet visus laukus!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(password1.isEmpty()) {
            Notification.show("Parole ir tukša").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (!password1.equals(password2)) {
            Notification.show("Paroles nesakrīt!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(user != null) {
            Notification.show("Tāds lietotājs jau pastāv").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(!emailAndPasswordValidation.validateEmail(email)) {
            Notification.show("Ievadiet pareizo e-pastu");
        } else if(!emailAndPasswordValidation.validatePassword(password1)) {
            Notification.show("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un vienu speciālu" +
                    "simbolu (#, $, %, ..)");
        } else {
            String token = UUID.randomUUID().toString();
            userDetailsService.register(firstName, lastName, email, password1, token);
            Notification notification = new Notification("Reģistrācija izdevās! Lūdzu, apstipriniet e-pastu! Jums ir sūtīta vēstule uz noradīto e-pastu");
            sendEmail(token, email);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(10000);
            notification.setPosition(Notification.Position.TOP_STRETCH);
            notification.open();
            getUI().ifPresent(ui -> ui.navigate("about"));
        }
    }

    private void sendEmail(String token, String email) {
        String subject = "Raksti.org - e-pasta apstiprināšana";
        String content = "Sveiki! Apsveicām ar reģistrāciju! Klikšķiniet uz saiti " + "http://localhost:8080/confirm-email/" +
                token + " un apstipriniet savu e-pastu!";
        emailSenderService.sendEmail(email, content, subject);
    }

    public void register(String email, String password1, String password2) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Notification.show("Tāds lietotājs jau pastāv").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(email.trim().isEmpty()) {
            Notification.show("Aizpildiet visus laukus").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(password1.isEmpty()) {
            Notification.show("Parole ir tukša").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (!password1.equals(password2)) {
            Notification.show("Paroles nesakrīt!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(!emailAndPasswordValidation.validateEmail(email)) {
            Notification.show("Ievadiet pareizo e-pastu");
        } else if(!emailAndPasswordValidation.validatePassword(password1)) {
            Notification.show("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un vienu speciālu" +
                    "simbolu (#, $, %, ..)");
        } else {
            String token = UUID.randomUUID().toString();
            userDetailsService.register(email, password1, token);
            Notification notification = new Notification("Reģistrācija izdevās! Lūdzu, apstipriniet e-pastu, lai pieslēgtos profilā! Jums ir sūtīta vēstule uz noradīto e-pastu");
            sendEmail(token, email);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setDuration(10000);
            notification.setPosition(Notification.Position.TOP_STRETCH);
            notification.open();
            getUI().ifPresent(ui -> ui.navigate("about"));
        }
    }

}
