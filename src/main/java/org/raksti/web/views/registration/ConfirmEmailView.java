package org.raksti.web.views.registration;

import org.raksti.web.data.entity.User;
import org.raksti.web.security.UserDetailsServiceImpl;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Email")
@Route("confirm-email/:emailToken/")
@AnonymousAllowed
public class ConfirmEmailView extends VerticalLayout implements BeforeEnterObserver {

    private String token;

    private final UserDetailsServiceImpl userDetailsService;

    public ConfirmEmailView(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;

        add("");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        token = beforeEnterEvent.getRouteParameters().get("emailToken").get();
        User user = userDetailsService.findByEmailConfirmationToken(token);
        if (user != null) {
            System.out.println("User found");
            user.setConfirmed(true);
            userDetailsService.updateUser(user);
            Notification.show("E-pasts apstiprināts", 7000, Notification.Position.TOP_STRETCH).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            beforeEnterEvent.forwardTo("about");
        } else {
            beforeEnterEvent.forwardTo("about");
            System.out.println("User not found");
        }
    }
}
