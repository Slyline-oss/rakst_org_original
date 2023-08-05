package org.raksti.web.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.data.entity.User;
import org.raksti.web.security.UserDetailsServiceImpl;
import org.raksti.web.views.registration.EmailAndPasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Reset Password")
@Route("reset-password/:token/")
@AnonymousAllowed
public class RestorePasswordView extends VerticalLayout implements BeforeEnterObserver {

    private final PasswordField password;
    private final PasswordField confirmPassword;
    private String token;

    private final UserDetailsServiceImpl userDetailsService;
    private final EmailAndPasswordValidation emailAndPasswordValidation;

    @Autowired
    public RestorePasswordView(UserDetailsServiceImpl userDetailsService, EmailAndPasswordValidation emailAndPasswordValidation) {
        this.userDetailsService = userDetailsService;
        this.emailAndPasswordValidation = emailAndPasswordValidation;

        Button submit = new Button();
        password = new PasswordField();
        confirmPassword = new PasswordField();

        submit.setText("Apstiprināt");
        password.setPlaceholder("Parole");
        confirmPassword.setPlaceholder("Parole");
        password.setLabel("Parole");
        confirmPassword.setLabel("Apstiprināt paroli");

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setHeight("100%");

        HorizontalLayout hl = new HorizontalLayout();
        hl.setJustifyContentMode(JustifyContentMode.BETWEEN);
        hl.setMaxWidth("500px");
        hl.add(password, confirmPassword, submit);
        hl.setSpacing(true);
        hl.getStyle().set("flex-wrap", "wrap");

        add(hl);

        submit.addClickListener(e -> setNewPassword());
    }

    private void setNewPassword() {
        if (!password.getValue().equals(confirmPassword.getValue())) {
            Notification.show("Paroles nesakrīt!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if (!emailAndPasswordValidation.validatePassword(password.getValue())) {
            Notification.show("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un ciparus",
                    5000, Notification.Position.TOP_START);
        } else {
            User user = userDetailsService.getByResetPasswordToken(token);
            userDetailsService.updateResetPasswordToken("",user.getEmail());
            userDetailsService.updatePassword(user.getEmail(),password.getValue());
            Notification not = new Notification();
            not.setPosition(Notification.Position.TOP_START);
            not.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            not.setText("Parole veiksmīgi nomainīta");
            not.setDuration(5000);
            not.open();
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
