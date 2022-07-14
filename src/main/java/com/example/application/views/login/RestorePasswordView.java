package com.example.application.views.login;

import com.example.application.data.entity.User;
import com.example.application.data.service.UserRepository;
import com.example.application.security.UserDetailsServiceImpl;
import com.example.application.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Reset Password")
@Route("reset-password/:token/")
@AnonymousAllowed
public class RestorePasswordView extends FormLayout implements BeforeEnterObserver {

    private Button submit;
    private PasswordField password;
    private PasswordField confirmPassword;
    private String token;


    @Autowired
    private EmailAndPasswordValidation emailAndPasswordValidation;

    private final UserDetailsServiceImpl userDetailsService;

    public RestorePasswordView(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;

        submit = new Button();
        password = new PasswordField();
        confirmPassword = new PasswordField();

        submit.setText("Apstiprināt");
        password.setPlaceholder("Parole");
        confirmPassword.setPlaceholder("Parole");
        password.setLabel("Parole");
        confirmPassword.setLabel("Apstiprināt paroli");

        setMaxWidth("500px");
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP)
        );
        add(password, confirmPassword, submit);
        setColspan(submit,2);

        submit.addClickListener(e -> setNewPassword());
    }

    private void setNewPassword() {
        if (!password.getValue().equals(confirmPassword.getValue())) {
            Notification.show("Paroles nesakrīt!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (!emailAndPasswordValidation.validatePassword(password.getValue())) {
            Notification.show("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un vienu speciālu" +
                    "simbolu (#, $, %, ..)");
        } else {
            User user = userDetailsService.getByResetPasswordToken(token);
            userDetailsService.updateResetPasswordToken("",user.getEmail());
            userDetailsService.updatePassword(user.getEmail(),password.getValue());
            Notification.show("Parole veiksmīgi izmainīta").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            getUI().ifPresent(ui -> ui.navigate("about"));
        }
    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        token = beforeEnterEvent.getRouteParameters().get("token").get();
        User user = userDetailsService.getByResetPasswordToken(token);
        if (user != null) {
            System.out.println("User found");
        } else {
            beforeEnterEvent.forwardTo("about");
            System.out.println("User not found");
        }
    }
}
