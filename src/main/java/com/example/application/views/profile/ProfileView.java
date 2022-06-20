package com.example.application.views.profile;

import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Profile")
@Route(value = "profile/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("USER")
public class ProfileView extends VerticalLayout {

    private TextField changePassword;
    private TextField user;
    private Button sayHello;
    private AuthenticatedUser authenticatedUser;


    public ProfileView(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            add(new Paragraph("Your name: " + user.getName()));
            add(new Paragraph("Your username: " + user.getUsername()));
            changePassword = new TextField();
            changePassword.setLabel("Change password");
            changePassword.setClearButtonVisible(true);
        }
        Button btnPassword = new Button("Change");
        btnPassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        btnPassword.addClickListener(buttonClickEvent -> {
            Notification notification = Notification.show("Password successfully changed!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.BOTTOM_END);
        });
        add(changePassword);
        add(btnPassword);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        //getStyle().set("text-align", "center");
    }

}
