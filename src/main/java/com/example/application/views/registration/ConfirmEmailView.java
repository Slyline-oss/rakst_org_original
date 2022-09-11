package com.example.application.views.registration;

import com.example.application.data.entity.User;
import com.example.application.security.UserDetailsServiceImpl;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Email")
@Route("confirm-email/:token/")
@AnonymousAllowed
public class ConfirmEmailView implements BeforeEnterObserver {

    private String token;

    private final UserDetailsServiceImpl userDetailsService;

    public ConfirmEmailView(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        token = beforeEnterEvent.getRouteParameters().get("token").get();
        User user = userDetailsService.findByEmailConfirmationToken(token);
        if (user != null) {
            System.out.println("User found");
            user.setConfirmed(true);
            userDetailsService.updateUser(user);
            Notification.show("E-pasts apstiprināts", 70000, Notification.Position.TOP_STRETCH).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            beforeEnterEvent.forwardTo("about");
        } else {
            beforeEnterEvent.forwardTo("about");
            System.out.println("User not found");
        }
    }
}
