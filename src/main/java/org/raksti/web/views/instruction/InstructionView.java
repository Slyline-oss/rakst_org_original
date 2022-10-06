package org.raksti.web.views.instruction;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.views.MainLayout;

@PageTitle("Instrukcija")
@Route(value = "instruction", layout = MainLayout.class)
@AnonymousAllowed
public class InstructionView extends VerticalLayout {


    public InstructionView() {
        landing();
    }

    private void landing() {
        getStyle().set("padding-top", "30px");

        HorizontalLayout hl = new HorizontalLayout();
        hl.setJustifyContentMode(JustifyContentMode.AROUND);
        hl.setSpacing(true);
        hl.setPadding(true);
        hl.setAlignItems(Alignment.CENTER);

        //Div image
        Div image = new Div();
        image.addClassNames("instruction-image");

        //Div content
        Div content = new Div();
        content.addClassNames("instruction-content");
        content.getStyle().set("width", "50%");

        //Image
        Image img = new Image("images/img.png", "main_image");
        image.add(img);
        img.getStyle().set("width", "810px");

        //style content
        content.setText("Esiet sveicināti VIII pasaules diktātā latviešu valodā! Teksta „Krāsaina saule virs pelēkiem jumtiem”nautors ir latviešu rakstnieks un žurnālists Osvalds Zebris. Diktāts norisināsies 15. oktobrī, plkst. 12.15 (pēc Latvijas laika). Lai rakstītu diktātu tiešsaistē, ir nepieciešama iepriekšēja reģistrācija. Piedaloties diktātā, Jūs piekrītat savu personas datu apstrādei, kas nekur netiks publicēti, taču ir nepieciešami saziņai un statistikai.\n" +
                "\n" +
                "Ekrāna kreisajā pusē Jūs redzēsiet un dzirdēsiet diktoru– režisoru un aktieri Gerdu Lapošku. Ekrāna labajā pusē būs teksta ievades lauks. Kad pabeigsiet rakstīt diktātu, būs jānospiež poga „Nosūtīt”, lai diktāts nonāktu pie mums labošanai. Nedēļu pēc diktāta norises anketā norādītajā e-pastā Jūs saņemsiet saiti, lai apskatītu izlaboto darbu savā profilā, kā arī virtuālu apliecinājumu par piedalīšanos.\n" +
                "\n" +
                "Lai redzētu video datorā, vēlams atjaunināt pārlūkprogrammas versiju. Interneta lejupielādes ātrumam jābūt vismaz 2 Mbps. Lūdzam pārliecināties, ka Jums ir pieejamas diakritiskās zīmes (garumzīmes un mīkstinājuma zīmes). Diktāti bez diakritiskajām zīmēm, kā arī tikai daļēji uzrakstīti diktāti pārbaudei pieņemti netiks.\n" +
                "\n" +
                "Vispirms diktors nolasīs visu tekstu. Tad diktēs pa vienam teikumam, to nolasot kopumā, pēc tam diktēs pa fragmentam un vēlreiz izlasīs visu teikumu. Pēc tam diktors vēlreiz nolasīs visu tekstu. Pēc diktāta uzrakstīšanas Jums būs 5 minūtes laika pārlasīšanai.\n" +
                "\n" +
                "Jums būs 3 minūtes laika, lai nosūtītu diktātu pārbaudei.\n" +
                "\n" +
                "Veiksmi vēlot,\n" +
                "\n" +
                "Toms Sadovskis un Olga Sukonnikova no raksti.org.");

        hl.add(content, image);
        add(hl);
    }

}
