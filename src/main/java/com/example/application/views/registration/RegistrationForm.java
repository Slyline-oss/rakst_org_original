package com.example.application.views.registration;

import com.example.application.data.entity.User;
import com.example.application.data.service.UserRepository;
import com.example.application.security.UserDetailsServiceImpl;
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

    public RegistrationForm(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, EmailAndPasswordValidation emailAndPasswordValidation) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.emailAndPasswordValidation = emailAndPasswordValidation;
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
           } else {
               firstName.setEnabled(true);
               lastName.setEnabled(true);
           }
        });

        facebookLogin.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("/oauth2/authorization/facebook"));
        });

        submitButton.addClickListener(e -> {
            register(firstName.getValue(), lastName.getValue(), email.getValue(), password.getValue(), passwordConfirm.getValue());
        });
    }


    public PasswordField getPasswordField() {
        return password;
    }

    public PasswordField getPasswordConfirmField() {
        return passwordConfirm;
    }

    public Span getErrorMessageField() {
        return errorMessageField;
    }

    public Button getSubmitButton() {
        return submitButton;
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
            userDetailsService.register(firstName, lastName, email, password1);
            Notification.show("Reģistrācija izdevās!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            getUI().ifPresent(ui -> ui.navigate("about"));
        }
    }

    public void register(String email, String password1, String password2) {

    }

}
