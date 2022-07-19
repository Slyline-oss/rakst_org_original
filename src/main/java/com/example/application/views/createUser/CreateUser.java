package com.example.application.views.createUser;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Create New User")
@Route(value = "create-user", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateUser extends VerticalLayout {

    private EmailField adminEmail;
    private Button createAdminButton;

    private final CreateUserController createUserController;

    public CreateUser(CreateUserController createUserController) {
        this.createUserController = createUserController;
        Text adminText = new Text("Izveidot jaunu adminu");
        adminEmail = new EmailField();
        adminEmail.setPlaceholder("e-pasts");
        adminEmail.setClearButtonVisible(true);
        adminEmail.setLabel("Ievadiet jaunā administratora e-pastu");
        adminEmail.setPattern("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        createAdminButton = new Button();
        createAdminButton.setText("Izveidot jaunu administratoru");
        add(adminText, adminEmail, createAdminButton);

        createAdminButton.addClickListener(e -> createUserController.sendEmail(adminEmail));

    }

}
