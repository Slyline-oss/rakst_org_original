package org.raksti.web.views.login;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.security.UserDetailsServiceImpl;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay {

    private final ForgotPasswordController forgotPasswordController;

    public LoginView(ForgotPasswordController forgotPasswordController) {
        this.forgotPasswordController = forgotPasswordController;

        setAction("login");
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form form = i18n.getForm();

        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Raksti");
        i18n.getHeader().setDescription("Ievadiet savu e-pastu un paroli");

        setForgotPasswordButtonVisible(true);
        form.setForgotPassword("Aizmirsi paroli?");
        form.setPassword("Parole");
        form.setUsername("E-pasts");
        form.setSubmit("Pieslēgties");
        form.setTitle("");

        addForgotPasswordListener(e -> forgotPasswordPopUp());

        i18n.setForm(form);
        setOpened(true);
        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18n.setAdditionalInformation("");
        i18n.setErrorMessage(i18nErrorMessage);
        setI18n(i18n);


    }



    private void forgotPasswordPopUp() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Paroles atjaunošana");
        dialog.setWidth("400px");
        dialog.setHeight("250px");
        dialog.setCloseOnOutsideClick(true);

        //Email Field
        EmailField email = new EmailField();
        email.setLabel("Ievadiet e-pastu");
        email.setPlaceholder("example@email.com");
        email.setErrorMessage("Lūdzu, ievadiet pareizo e-pastu");
        email.setClearButtonVisible(true);
        email.setPattern("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        email.setMinWidth("250px");

        //Create Buttons
        Button sendButton = new Button();
        sendButton.setText("Nosūtīt");
        sendButton.addClickListener(e -> {
           forgotPasswordController.sendEmail(email);
           dialog.close();
        });

        Button cancelButton = new Button("Aizvert");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(e -> dialog.close());

        //Horizontal layout
        HorizontalLayout hl = new HorizontalLayout();
        hl.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        hl.setSpacing(true);
        hl.add(cancelButton, sendButton);

        dialog.add(email);
        dialog.getFooter().add(hl);

        dialog.open();
    }

}
