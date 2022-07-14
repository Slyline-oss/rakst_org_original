package com.example.application.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;


@PageTitle("Forgot Password")
@Route(value = "forgot-password")
@AnonymousAllowed
public class ForgotPassword extends VerticalLayout {

    private Button submit;
    private EmailField email;
    private HttpRequest httpRequest;

    @Autowired
    private ForgotPasswordController forgotPasswordController;



    public ForgotPassword() {

        email = new EmailField();
        submit = new Button();

        email.setLabel("Ievadiet e-pastu");
        email.setPlaceholder("example@email.com");
        email.setErrorMessage("Lūdzu, ievadiet pareizo e-pastu");
        email.setClearButtonVisible(true);
        email.setPattern("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        submit.setText("nosūtīt");
        submit.addClickListener(e -> sendEmail());

        add(email,submit);
    }

   private void sendEmail() {
        forgotPasswordController.sendEmail(email);
        getUI().ifPresent(ui -> ui.navigate("about"));
   }

}
