package com.example.application.views.profile;

import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.security.UserDetailsServiceImpl;
import com.example.application.views.MainLayout;
import com.example.application.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@PageTitle("Profile")
@Route(value = "profile/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("USER")
public class ProfileView extends VerticalLayout {

    private final PasswordField password;
    private final PasswordField newPassword;
    private final AuthenticatedUser authenticatedUser;
    private final TextField name;
    private final TextField surname;
    private final DatePicker birthDate;
    private final Locale LATVIAN_LOCALE = new Locale("lv", "LV");
    private final TextField telNumber;
    private final Select<String> language;
    private com.vaadin.flow.component.dialog.Dialog confirmationDialog;

    private final UserDetailsServiceImpl userDetailsService;

    private final EmailAndPasswordValidation emailAndPasswordValidation;
    private User user;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileView(AuthenticatedUser authenticatedUser, UserDetailsServiceImpl userDetailsService, EmailAndPasswordValidation emailAndPasswordValidation) {
        this.authenticatedUser = authenticatedUser;
        this.userDetailsService = userDetailsService;
        this.emailAndPasswordValidation = emailAndPasswordValidation;
        Optional<User> maybeUser = authenticatedUser.get();
        maybeUser.ifPresent(value -> this.user = value);

        DatePicker.DatePickerI18n latvianI18n = new DatePicker.DatePickerI18n();
        latvianI18n.setMonthNames(List.of("Janv??ris", "Febru??ris", "Marts", "Apr??lis", "Maijs",
                "J??nijs", "J??lijs", "Augusts", "Septembris", "Oktobris", "Novembris", "Decembris"));
        latvianI18n.setWeekdays(List.of("Sv??tdiena", "Pirmdiena", "Otrdiena", "Tre??diena", "Ceturtdiena", "Piektdiena", "Sestdiena" ));
        latvianI18n.setWeekdaysShort(List.of("Sv", "P", "O", "T", "C", "Pk", "S"));
        latvianI18n.setFirstDayOfWeek(1);
        latvianI18n.setWeek("Ned????a");
        latvianI18n.setToday("??odien");

        language = new Select<>();
        language.setLabel("J??su dzimt?? valoda");


        name = new TextField("J??su v??rds:");
        name.setPlaceholder("V??rds");

        surname = new TextField("J??su uzv??rds:");
        surname.setPlaceholder("Uzv??rds");

        birthDate = new DatePicker();
        birthDate.setPlaceholder("DD.MM.YY");
        birthDate.setLabel("Dzim??an??s diena");
        birthDate.setI18n(latvianI18n);

        telNumber = new TextField("J??su telefona numurs:");

        Button submitBut = new Button();
        submitBut.setText("Apstiprin??t");
        submitBut.addClickListener(e -> confirmationDialog.open());

        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout firstLayout = new VerticalLayout();
        VerticalLayout secondLayout = new VerticalLayout();
        firstLayout.add(name, birthDate, submitBut);
        secondLayout.add(surname, telNumber, language);
        layout.add(firstLayout, secondLayout);
        layout.setPadding(true);

        confirmationDialog = new Dialog();
        confirmationDialog.setHeaderTitle("Saglab??t izmai??as?");
        Button saveButton = new Button("Saglab??t");
        saveButton.addClickListener(e -> saveChanges());
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        Button cancelButton = new Button("Atteikties", e -> confirmationDialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        confirmationDialog.getFooter().add(cancelButton, saveButton);


        password = new PasswordField();
        password.setLabel("J??su teko??a parole: ");
        password.setClearButtonVisible(true);


        newPassword = new PasswordField();
        newPassword.setLabel("Jauna parole: ");
        newPassword.setClearButtonVisible(true);
        newPassword.setPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()???[{}]:;',?/*~$^+=<>]).{8,20}$");
        newPassword.setHelperText("Parolei j??b??t vism??z 8 simbolu garai, iek??aujot lielus, mazus burtus un vienu speci??lu\" +\n" +
                "\"simbolu (#, $, %, ..)");

        Button btnPassword = new Button("Change");
        btnPassword.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        btnPassword.addClickListener(buttonClickEvent -> changePassword(password.getValue(), newPassword.getValue()));

        add(new H5("J??su dati:"));
        add(layout);
        add(new H5("Pamain??t paroli"));
        add(password);
        add(newPassword);
        add(btnPassword);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        //getStyle().set("text-align", "center");

//        completeProfileNotification();

        try {
            throw new NullPointerException();
        } catch(Exception e) {
            System.out.println("Error occurred");
        }
    }


    @PostConstruct
    private void initializeUser() {
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            user = maybeUser.get();
            name.setValue(user.getFirstName());
            surname.setValue(user.getLastName());
            birthDate.setLocale(LATVIAN_LOCALE);
            birthDate.setValue(user.getBirthday());
            telNumber.setValue(user.getTelNumber());
            language.setItems("Latvie??u", "Krievu", "V??cu", "Ang??u", "Cit?? sve??valoda");
            language.setValue(user.getLanguage());
        }
    }


    private void saveChanges() {
        Optional<User> maybeUser = authenticatedUser.get();
        User newUser;
        if (maybeUser.isPresent()) {
            newUser = maybeUser.get();
            newUser.setLanguage(language.getValue());
            newUser.setFirstName(name.getValue());
            newUser.setLastName(surname.getValue());
            newUser.setBirthday(birthDate.getValue());
            newUser.setTelNumber(telNumber.getValue());
            userDetailsService.updateUser(newUser);
            confirmationDialog.close();
            Notification.show("J??su dati veiksm??gi saglab??ti!").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }


    private void completeProfileNotification() {
        if (language.getValue() == null || name.getValue() == null || surname.getValue() == null
        || birthDate.getValue() == null || telNumber.getValue() == null) {
            Notification.show("L??dzu papildiniet inform??ciju profil??!");
        }
    }

    private void changePassword(String password, String newPassword) {
        if (newPassword.trim().isEmpty() || password.trim().isEmpty()) {
            Notification.show("Aizpildiet visus laukus!").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(!passwordEncoder.matches(password, user.getHashedPassword())) {
            Notification.show("Tiek ievad??ta nepareiza teko??a parole").addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else if(!emailAndPasswordValidation.validatePassword(newPassword)) {
            Notification.show("Parolei j??b??t vism??z 8 simbolu garai, iek??aujot lielus, mazus burtus un vienu speci??lu" +
                    "simbolu (#, $, %, ..)");
        } else {
            userDetailsService.updatePassword(user.getEmail(), newPassword);
            Notification notification = Notification.show("Parole veiksm??gi pamain??ta!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.BOTTOM_END);
        }

    }


}
