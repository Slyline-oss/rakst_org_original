package org.raksti.web.views.createUser;

import org.raksti.web.countriesAndLanguages.CountriesAndLanguages;
import org.raksti.web.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Locale;

@PageTitle("Izveidot jaunu adminu")
@Route(value = "create-user", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateUser extends VerticalLayout {

    private EmailField adminEmail;
    private Button createAdminButton;
    private Button createUserButton;

    private final TextField name = new TextField("Vārds:");
    private final EmailField userEmail = new EmailField("E-pasts");
    private final TextField surname = new TextField("Uzvārds:");;
    private final DatePicker birthDate = new DatePicker();;
    private final Locale LATVIAN_LOCALE = new Locale("lv", "LV");
    private final TextField telNumber = new TextField("Telefona numurs:");;
    private final Select<String> language = new Select<>();
    private final TextField age = new TextField("Vecums:");
    private final TextField city = new TextField("Pilsēta:");
    private final Select<String> gender = new Select<>();
    private final Select<String> country = new Select<>();
    private final Select<String> education = new Select<>();

    private final CreateUserController createUserController;
    private final CountriesAndLanguages countriesAndLanguages;

    public CreateUser(CreateUserController createUserController, CountriesAndLanguages countriesAndLanguages) {
        this.createUserController = createUserController;
        this.countriesAndLanguages = countriesAndLanguages;
        Text adminText = new Text("Izveidot jaunu adminu");
        Text userText = new Text("Izveidot jaunu lietotāju");
        adminEmail = new EmailField();
        adminEmail.setPlaceholder("e-pasts");
        adminEmail.setClearButtonVisible(true);
        adminEmail.setLabel("Ievadiet jaunā administratora e-pastu");
        adminEmail.setPattern("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        createAdminButton = new Button();
        createUserButton = new Button("Izveidot jaunu lietotāju");
        createAdminButton.setText("Izveidot jaunu administratoru");
        add(adminText, adminEmail, createAdminButton, userText);
        createUserRegistration();
        add(createUserButton);
        createAdminButton.addClickListener(e -> {
            createUserController.sendEmailAdmin(adminEmail);
        });
        createUserButton.addClickListener(e -> {
            createUserController.sendEmailUser(userEmail, name.getValue(), surname.getValue(), birthDate.getValue(), telNumber.getValue(), language.getValue(),
                    country.getValue(), city.getValue(), age.getValue(), education.getValue(), gender.getValue());
        });
    }

    public void createUserRegistration() {
        DatePicker.DatePickerI18n latvianI18n = new DatePicker.DatePickerI18n();
        latvianI18n.setMonthNames(List.of("Janvāris", "Februāris", "Marts", "Aprīlis", "Maijs",
                "Jūnijs", "Jūlijs", "Augusts", "Septembris", "Oktobris", "Novembris", "Decembris"));
        latvianI18n.setWeekdays(List.of("Svētdiena", "Pirmdiena", "Otrdiena", "Trešdiena", "Ceturtdiena", "Piektdiena", "Sestdiena" ));
        latvianI18n.setWeekdaysShort(List.of("Sv", "P", "O", "T", "C", "Pk", "S"));
        latvianI18n.setFirstDayOfWeek(1);
        latvianI18n.setWeek("Nedēļa");
        latvianI18n.setToday("Šodien");

        userEmail.setPlaceholder("E-pasts");
        userEmail.setPattern("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

        language.setLabel("Dzimtā valoda");
        language.setPlaceholder("Valoda");

        telNumber.setPlaceholder("Telefona numurs");

        name.setPlaceholder("Vārds");

        surname.setPlaceholder("Uzvārds");

        birthDate.setPlaceholder("DD.MM.YY");
        birthDate.setLabel("Dzimšanas diena");
        birthDate.setI18n(latvianI18n);
        birthDate.setLocale(LATVIAN_LOCALE);

        language.setItems(countriesAndLanguages.returnLanguages());

        country.setLabel("Dzimtā valsts");
        country.setPlaceholder("Valsts");
        country.setItems(countriesAndLanguages.returnCountries());

        city.setPlaceholder("Pilsēta");

        gender.setLabel("Jūsu dzimums");
        gender.setPlaceholder("Dzimums");
        gender.setItems("Vīrietis", "Sieviete", "Cits");

        education.setLabel("Izglītība");
        education.setPlaceholder("Izglītība");
        education.setItems("Pamatizglītība", "Vidējā izglītība", "Bakalaurs", "Maģistrs", "Doktors");


        HorizontalLayout layout = new HorizontalLayout();
        VerticalLayout firstLayout = new VerticalLayout();
        VerticalLayout secondLayout = new VerticalLayout();
        firstLayout.add(name, birthDate, userEmail, country, gender, age);
        secondLayout.add(surname, telNumber, language, city, education);
        layout.add(firstLayout, secondLayout);
        layout.setPadding(true);

        add(layout);
    }

}
