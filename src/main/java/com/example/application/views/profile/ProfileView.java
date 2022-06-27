package com.example.application.views.profile;

import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.security.UserDetailsServiceImpl;
import com.example.application.views.MainLayout;
import com.example.application.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Profile")
@Route(value = "profile/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("USER")
public class ProfileView extends VerticalLayout {

    private PasswordField password;
    private PasswordField newPassword;
    private AuthenticatedUser authenticatedUser;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    private final EmailAndPasswordValidation emailAndPasswordValidation;
    private User user;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileView(AuthenticatedUser authenticatedUser, UserDetailsServiceImpl userDetailsService, EmailAndPasswordValidation emailAndPasswordValidation) {
        this.authenticatedUser = authenticatedUser;
        this.userDetailsService = userDetailsService;
        this.emailAndPasswordValidation = emailAndPasswordValidation;

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            user = maybeUser.get();
            add(new Paragraph("Jūsu vārds: " + user.getFirstName() + " " + user.getLastName()));
            add(new Paragraph("Jūsu nikneims: " + user.getUsername()));
        }

        password = new PasswordField();
        password.setLabel("Jūsu tekoša parole: ");
        password.setClearButtonVisible(true);


        newPassword = new PasswordField();
        newPassword.setLabel("Jauna parole: ");
        newPassword.setClearButtonVisible(true);
        newPassword.setPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
        newPassword.setHelperText("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un vienu speciālu\" +\n" +
                "\"simbolu (#, $, %, ..)");

        Button btnPassword = new Button("Change");
        btnPassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        btnPassword.addClickListener(buttonClickEvent -> changePassword(password.getValue(), newPassword.getValue()));
        add(new H5("Pamainīt paroli"));
        add(password);
        add(newPassword);
        add(btnPassword);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        //getStyle().set("text-align", "center");

        try {
            throw new NullPointerException();
        } catch(Exception e) {
            System.out.println("Error occurred");
        }
    }

    private void changePassword(String password, String newPassword) {
        if (newPassword.trim().isEmpty() || password.trim().isEmpty()) {
            Notification.show("Aizpildiet visus laukus!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(!passwordEncoder.matches(password, user.getHashedPassword())) {
            Notification.show("Tiek ievadīta nepareiza tekoša parole").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(!emailAndPasswordValidation.validatePassword(newPassword)) {
            Notification.show("Parolei jābūt vismāz 8 simbolu garai, iekļaujot lielus, mazus burtus un vienu speciālu" +
                    "simbolu (#, $, %, ..)");
        } else {
            userDetailsService.updatePassword(user.getEmail(), newPassword);
            Notification notification = Notification.show("Parole veiksmīgi pamainīta!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.BOTTOM_END);
        }

    }


}
