package com.example.application.views.registration;

import com.example.application.data.service.UserRepository;
import com.example.application.security.UserDetailsServiceImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Registration")
@Route(value = "registration")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

   private final UserDetailsServiceImpl userDetailsService;
   private final UserRepository userRepository;
   @Autowired
   private final EmailAndPasswordValidation emailAndPasswordValidation;

   public RegistrationView(UserDetailsServiceImpl userDetailsService, UserRepository userRepository, EmailAndPasswordValidation emailAndPasswordValidation) {
       this.userDetailsService = userDetailsService;
       this.userRepository = userRepository;
       this.emailAndPasswordValidation = emailAndPasswordValidation;
       RegistrationForm registrationForm = new RegistrationForm(this.userDetailsService, this.userRepository, this.emailAndPasswordValidation);
       setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

       add(registrationForm);

   }


}
