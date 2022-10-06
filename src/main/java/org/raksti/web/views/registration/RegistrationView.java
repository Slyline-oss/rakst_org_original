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

   private final UserDetailsServiceImpl userDetailsService;
   private final UserRepository userRepository;
   @Autowired
   private final EmailAndPasswordValidation emailAndPasswordValidation;
   private final EmailSenderService emailSenderService;

   public RegistrationView(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, EmailAndPasswordValidation emailAndPasswordValidation, EmailSenderService emailSenderService) {
       this.userDetailsService = userDetailsService;
       this.userRepository = userRepository;
       this.emailAndPasswordValidation = emailAndPasswordValidation;
       this.emailSenderService = emailSenderService;
       RegistrationForm registrationForm = new RegistrationForm(this.userDetailsService, this.userRepository, this.emailAndPasswordValidation, this.emailSenderService);
       setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

       add(registrationForm);

   }


}
