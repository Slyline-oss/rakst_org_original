package org.raksti.web.views.sponsors;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.data.entity.Sponsor;
import org.raksti.web.data.service.SponsorService;
import org.raksti.web.views.MainLayout;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;



@PageTitle("Par inciatīvu")
@Route(value = "sponsors", layout = MainLayout.class)
@AnonymousAllowed
public class SponsorView extends VerticalLayout {

    private final List<Sponsor> sponsorList;

    public SponsorView(SponsorService sponsorService) {
        sponsorList = sponsorService.getAllEntities();
        Collections.sort(sponsorList);
        addClassNames("sponosrs-view");
        landing();
    }

    public void landing() {
        getStyle().set("padding-top", "30px");
        //Div Titles
        Div titles = new Div();
        titles.addClassNames("logo-titles");

        H1 sponsorTitle = new H1("Atbalstītāji");
        H1 placesTitle = new H1("Norises vietas");

        titles.add(sponsorTitle, placesTitle);

        //Div wrapper
        Div wrapper = new Div();
        wrapper.addClassNames("logo-wrapper");

        //Div second wrapper
        Div wrapper2 = new Div();
        wrapper2.addClassNames("text-wrapper");

        //Div sponsors
        Div sponsors = new Div();
        sponsors.addClassNames("sponsors");

        //Div places
        Div places = new Div();
        places.addClassNames("places");

        //add sponsors and places
        for (Sponsor sponsor: sponsorList) {
            Anchor link = new Anchor(sponsor.getLink());
            link.setTarget("blank");
            StreamResource sr = new StreamResource("logo", () -> new ByteArrayInputStream(sponsor.getImage()));
            sr.setContentType("image/png");
            Image image = new Image(sr, sponsor.getName());
            image.addClassNames("sponsors-image");
            link.add(image);
            if (sponsor.getType().equals("Atbalstītājs")) {
                sponsors.add(new Div(link));
            } else {
                places.add(new Div(link));
            }
        }


        //Div about exam
        Div aboutExam = new Div();
        aboutExam.addClassNames("aboutExam");
        aboutExam.setText("Nodibinājums “Pasaules valoda”, kam ir sabiedriskā labuma organizācijas statuss, piedāvā iespēju ikvienam interesentam katru gadu rudenī piedalīties Pasaules diktātā latviešu valodā un " +
                "rakstīt kāda mūsdienu latviešu autora īpaši šim notikumam radītu tekstu, ko diktē slavens latviešu aktieris. Diktāta rakstīšana ir brīvprātīgs bezmaksas pasākums, kas reizi gadā vienlaikus norisinās klātienē vairākās Latvijas pilsētās un " +
                "tiešsaistē visā pasaulē. Šogad diktāts norisināsies 14. oktobrī pulksten 12.15 pēc Latvijas laika.\n" +
                "Šīs iniciatīvas mērķi ir attīstīt latviešu valodas rakstītprasmi un interesi par to, audzināt cieņu pret dzimto valodu jau no bērnības, " +
                "veidot ciešāku saikni ar ārzemēs dzīvojošiem latviešiem, saliedēt sabiedrību, kā arī rosināt cittautiešus labāk apgūt latviešu valodu.");

        //Div principles text
        Div principles = new Div();
        principles.addClassNames("principles");
        principles.setText("Diktāta rakstīšana ir brīvprātīgs bezmaksas pasākums ikvienam interesentam visā pasaulē.\n" +
                "\n" +
                "Brīvprātīgs – neviens nevar tikt piespiests rakstīt diktātu ar varu vai tikt iesaistīts tā organizēšanā.\n" +
                "\n" +
                "Bezmaksas – iekasēt samaksu par dalību diktāta rakstīšanā, kā arī par darbu labotāju konsultācijām ir aizliegts. Organizatoru pienākums ir nodrošināt katram klātienē rakstītgribētājam pildspalvu un numurētu veidlapu diktāta rakstīšanai.\n" +
                "\n" +
                "Visiem interesentiem – nevienam nedrīkst būt atteikta dalība diktāta rakstīšanā, kā atteikuma iemeslu minot viņa vecumu, dzimumu, seksuālo orientāciju, pilsonību, tautību vai latviešu valodas zināšanu līmeni. Diktātu raksta arī vājredzīgi, neredzīgi, vājdzirdīgi un nedzirdīgi cilvēki.\n" +
                "\n" +
                "Iespējamas anonimitātes princips – nevienam no dalībniekiem nav jānorāda savs vārds un uzvārds, ja viņš to nevēlas darīt.\n" +
                "\n" +
                "Katru gadu diktāts tiek translēts ne tikvien tīmekļvietnē „raksti.org” un sabiedrisko mediju portālā „LSM.lv”, bet arī Latvijas Radio 1 un Latvijas Televīzijā.");

        Div posti = new Div();
        posti.getStyle().set("width", "50%");
        posti.setText("Pasaules diktāta latviešu valodā portāls raksti.org ir tapis pateicoties mūsu lieldraugam, Somijas pastam piederošajam uzņēmumam Posti Messaging.");
        wrapper2.add(aboutExam, principles);
        wrapper.add(sponsors, places);
        add(wrapper2, titles, wrapper);
    }


}
