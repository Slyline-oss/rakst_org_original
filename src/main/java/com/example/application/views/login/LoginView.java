package com.example.application.views.login;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay {
    public LoginView() {
        setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Raksti");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        setForgotPasswordButtonVisible(true);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        setOpened(true);
        i18n.setAdditionalInformation("Please, contact admin@company.com if you're experiencing issues logging into your account");
        i18n.setErrorMessage(i18nErrorMessage);

        setI18n(i18n);


    }

}
