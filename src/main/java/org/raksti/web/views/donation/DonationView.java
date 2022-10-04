package org.raksti.web.views.donation;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.data.entity.About;
import org.raksti.web.data.entity.DonateText;
import org.raksti.web.data.service.DonateService;
import org.raksti.web.views.MainLayout;

import java.util.List;


@PageTitle("Atbalsts un saziņa")
@Route(value = "donates", layout = MainLayout.class)
@AnonymousAllowed
public class DonationView extends VerticalLayout {

    private final TextField name = new TextField("Vārds");
    private final TextField surname = new TextField("Uzvārds");
    private final TextField email = new TextField("E-pasts");
    private final TextArea info = new TextArea("Ziņa");
    private final Button send = new Button("Sūtīt");

    private final DonateService donateService;

    public DonationView(DonateService donateService) {
        this.donateService = donateService;
        landing();
    }

    private void landing() {
        List<DonateText> donateTextList = donateService.getAll();
        DonateText donateText = null;
        if (donateTextList.size() > 0) {
            donateText = donateTextList.get(0);
        }
        // br html component
        HtmlComponent br = new HtmlComponent("br");
        //Content wrapper
        Div wrapper = new Div();
        wrapper.addClassNames("content-wrapper");
        //Contacts description
        Div contactsDescription = new Div();
        contactsDescription.addClassNames("contacts-description");
        //Contacts form
        Div contactsForm = new Div();
        contactsForm.addClassNames("contacts-form");

        //Div info about donation
        Div info = new Div();
        info.addClassNames("contacts-info");
        info.setText(donateText != null ? donateText.getText() : "");

        //Content for contacts description
        Paragraph one = new Paragraph();
        one.setText("Latviešu valodas cienītāji! Lai Pasaules diktāts latviešu valodā notiktu arī šoruden, lūdzam sniegt finansiālu atbalstu, " +
                "jo mūsu nesavtīgā entuziasma un personīgo ieguldījumu iespējas septiņu gadu garumā ir izsmeltas:");
        //Paragraph for mobilly
        Paragraph mobilly = new Paragraph();
        Anchor mobillySrc = new Anchor("https://mobilly.lv/ziedojumi/#/katalogs/7/9/1265");
        mobillySrc.setTarget("blank");
        Image paypalImg = new Image("images/paypal.png", "paypal");
        Anchor paypalSrc = new Anchor("https://www.paypal.com/donate/?hosted_button_id=DULQA7Q2H6DNE");
        paypalSrc.setTarget("blank");
        paypalSrc.add(paypalImg);
        Image mobillyQr = new Image("images/mobily_qr.png", "mobilly qr");
        mobillySrc.add(mobillyQr);
        mobillyQr.addClassNames("mobilly-qr");
        mobilly.add(paypalSrc, new HtmlComponent("br"), mobillySrc, new HtmlComponent("br"));
        //Paragraph adress
        Div adress = new Div();
        adress.add(new Paragraph("Nodibinājums „Pasaules valoda”"), new Paragraph("Vienotais reģistrācijas numurs: 40008282238"),
                new Paragraph("Adrese: Sliežu iela 7, Rīga, Latvija, LV-1005"), new Paragraph("Banka: AS Swedbank"),
                new Paragraph("Konts: LV94HABA0551046120176"), new Paragraph("Maksājuma mērķis: Ziedojums."));

        Div infoContacts = new Div();
        infoContacts.setText("Ja ir kādi jautājumi vai ierosinājumi – sazinieties ar mums: " +
                "raksti@raksti.org vai +371 29441292 (Olga Sukonnikova)");

        Div facebook = new Div();
        Anchor facebookLink = new Anchor("https://www.facebook.com/diktats", "Facebook lapai!");
        facebook.setText("Pievienojieties mūsu ");
        facebook.add(facebookLink);


        Div lastVideo = new Div();
        lastVideo.add(new Anchor("https://www.youtube.com/channel/UCEOvbGmiaDaOnjTPQg9Cg8g/","Noskatieties vēlreiz iepriekšējo diktātu video!"));

        Div policy = new Div();
        policy.setText("Šajā tīmekļvietnē esošo informāciju drīkst un ir vēlams pārpublicēt citos resursos.");

        contactsDescription.add(info, one, mobilly, adress, infoContacts, facebook, lastVideo, policy);

        //Contact form
        name.setMinWidth("400px");
        surname.setMinWidth("400px");
        email.setMinWidth("400px");

        contactsForm.add(name, surname, email, send);

        wrapper.add(contactsDescription);
        add(wrapper);
    }

}
