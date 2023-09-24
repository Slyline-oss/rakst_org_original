package org.raksti.web.views.registration;

import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.security.UserDetailsServiceImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Registration")
@Route(value = "registration", layout = MainLayout.class)
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    @Autowired
    public RegistrationView(UserDetailsServiceImpl userDetailsService, UserRepository userRepository,
                            EmailAndPasswordValidator emailAndPasswordValidation, EmailSenderService emailSenderService) {
        RegistrationForm registrationForm = new RegistrationForm(
                userDetailsService, userRepository, emailAndPasswordValidation, emailSenderService);

        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        add(registrationForm);
    }


}
