package org.raksti.web.views.login;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay {
    public LoginView() {
        setAction("login");
        LoginI18n i18n = LoginI18n.createDefault();
        LoginI18n.Form form = i18n.getForm();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Raksti");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        setForgotPasswordButtonVisible(true);
        form.setForgotPassword("Vai aizmirsi paroli?");
        form.setPassword("Parole");
        form.setUsername("E-pasts");
        form.setSubmit("Pieslēgties");
        form.setTitle("");

        addForgotPasswordListener(e -> getUI().ifPresent(
                ui -> ui.navigate("forgot-password")
        ));

        i18n.setForm(form);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        setOpened(true);
        i18n.setAdditionalInformation("Sazināties pa e-pasat ...");
        i18n.setErrorMessage(i18nErrorMessage);

        setI18n(i18n);
        Button facebookLogin = new Button();
        facebookLogin.setText("Pieslēgties ar Facebook");

    }

}
