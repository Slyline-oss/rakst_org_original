package org.raksti.web.views.originalText;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.data.entity.Pages;
import org.raksti.web.data.service.PagesService;
import org.raksti.web.views.MainLayout;

import java.util.Optional;

@PageTitle("Diktāta oriģinālteksts")
@Route(value = "original-text", layout = MainLayout.class)
@AnonymousAllowed
public class OriginalTextView extends VerticalLayout implements BeforeEnterObserver {

    private final PagesService pagesService;

    public OriginalTextView(PagesService pagesService) {
        this.pagesService = pagesService;
        textWithoutComments();
        add(new HtmlComponent("br"));
        textWithComments();
        add(new HtmlComponent("br"));
        comments();
    }

    private void textWithoutComments() {
        H1 author = new H1();
        author.setText("Māris Bērziņš");

        H2 title = new H2();
        title.setText("Izdomāt diktātu");

        Div paragraphs = new Div();
        paragraphs.add(new Paragraph("Pamostos ar nemierīgu sajūtu, it kā kaut ko būtu aizmirsis izdarīt. Ak, man taču ir jāizdomā pasaules diktāts latviešu valodā! Gribēju to paveikt pagājušajā rudenī, taču atliku citu darbu dēļ. Tuvojoties pavasarim, atkal iedomājos par diktātu, bet vēlreiz atliku, gandrīz pavisam to aizmirsdams."));
        paragraphs.add(new Paragraph("Nu gan pietiks prokrastinēt, jāizdara darbs, lai vienreiz ir miers. Diktātam jābūt aptuveni trīssimt vārdu garam. Droši vien tīrais nieks, ja pacenstos, varbūt pa dienu vai divām dabūtu gatavu. Tematikas ziņā ierobežojumu nav, galvenais nosacījums, lai tekstā būtu salikti visādi klupšanas akmeņi."));
        paragraphs.add(new Paragraph("Jāpaskatās, kādi tie diktāta veidošanas nosacījumi īsti bija. Meklēdams pērn atsūtīto vēstuli, pamanu, ka neesmu savlaicīgi reaģējis uz vairākiem citiem sūtījumiem un ka tie nevar gaidīt. Vēlāk paredzētas citas neatliekamas darīšanas, diktātam varēšu pievērsties tikai rīt vai parīt. Tomēr vēstuli izlasu, un, maigi sakot, tā mani saviļņo."));
        paragraphs.add(new Paragraph("Tekstā jāizmanto dažādas sintaktiskās konstrukcijas, jāiemāna iespraudumi, partikulas, divdabja teicieni un kas tik vēl ne. Tā vien izskatās, ka diktātā jāieliek pilnīgi visi gramatiskie stiķi, kādi vien Latvijas dabā atrodami. Jābūt ne tikvien saliktiem teikumiem ar vienlīdzīgiem palīgteikumiem, bet arī teikumiem, kuros kādu no daļām ievada saiklis vai kāds cits no pakārtojuma vārdiem, pēc kura tūlīt seko divdabja teiciens. Tad vēl vajadzīgas plašākas obligāti savrupināmas grupas ar divdabi pamatā. Kas tie par brīnumiem, un kā tie izskatās?"));
        paragraphs.add(new Paragraph("No terminoloģijas vien mati ceļas stāvus gaisā, kaut gan tie ir nogriezti uz nullīti. Bet tas vēl nav viss. Diktātā jāiekļauj vairāki vārdi, kuru rakstība īpaši iegaumējama vai pārbaudāma ar konkrētiem likumiem, pāris plaši izmantojamu svešvārdu, vārda “lūzt” formas. Jā, ar to laušanu un lūšanu visādi gājis. Skolotāja skaitīja: “Vētrā visi koki lūst, bet resnais ozols negrib lūzt.” Diez kas nav."));
        paragraphs.add(new Paragraph("Laikam prātīgāk būtu vispirms izveidot pašu tekstu, cerot, ka visus šos gramatiskos āķus iestrādāšu pēc tam. Tagad tikai jāizdomā, par ko rakstīt, taču par to es domāšu rīt."));

        add(author, title, paragraphs);
    }

    private void textWithComments() {
        H1 author = new H1("Māris Bērziņš");
        H2 title = new H2("Izdomāt diktātu");
        H3 comments = new H3("Teksts ar komentāriem");

        Div paragraphs = new Div();
        paragraphs.add(new Paragraph("Pamostos ar nemierīgu sajūtu,(1) it kā kaut ko būtu aizmirsis izdarīt.(2) Ak,(3) man taču ir jāizdomā pasaules diktāts latviešu valodā!(4) Gribēju to paveikt pagājušajā rudenī,(5) taču atliku citu darbu dēļ.(6) Tuvojoties pavasarim,(7) atkal iedomājos par diktātu,(8) bet vēlreiz atliku,(9) gandrīz pavisam to aizmirsdams.(10)"));
        paragraphs.add(new Paragraph("Nu gan pietiks prokrastinēt,(11) jāizdara darbs,(12) lai vienreiz ir miers.(13) Diktātam jābūt aptuveni trīssimt vārdu garam.(14) Droši vien tīrais nieks,(15) ja pacenstos,(16) varbūt pa dienu vai divām dabūtu gatavu.(17) Tematikas ziņā ierobežojumu nav,(18) galvenais nosacījums,(19) lai tekstā būtu salikti visādi klupšanas akmeņi.(20)"));
        paragraphs.add(new Paragraph("Jāpaskatās,(21) kādi tie diktāta veidošanas nosacījumi īsti bija.(22) Meklēdams pērn atsūtīto vēstuli,(23) pamanu,(24) ka neesmu savlaicīgi reaģējis uz vairākiem citiem sūtījumiem un ka tie nevar gaidīt.(25) Vēlāk paredzētas citas neatliekamas darīšanas,(26) diktātam varēšu pievērsties tikai rīt vai parīt.(27) Tomēr vēstuli izlasu,(28) un,(29) maigi sakot,(30) tā mani saviļņo.(31)"));
        paragraphs.add(new Paragraph("Tekstā jāizmanto dažādas sintaktiskās konstrukcijas,(32) jāiemāna iespraudumi,(33) partikulas,(34) divdabja teicieni un kas tik vēl ne.(35) Tā vien izskatās,(36) ka diktātā jāieliek pilnīgi visi gramatiskie stiķi,(37) kādi vien Latvijas dabā atrodami.(38) Jābūt ne tikvien saliktiem teikumiem ar vienlīdzīgiem palīgteikumiem,(39) bet arī teikumiem,(40) kuros kādu no daļām ievada saiklis vai kāds cits no pakārtojuma vārdiem,(41) pēc kura tūlīt seko divdabja teiciens.(42) Tad vēl vajadzīgas plašākas obligāti savrupināmas grupas ar divdabi pamatā.(43) Kas tie par brīnumiem,(44) un kā tie izskatās?(45)"));
        paragraphs.add(new Paragraph("No terminoloģijas vien mati ceļas stāvus gaisā,(46) kaut gan tie ir nogriezti uz nullīti.(47) Bet tas vēl nav viss.(48) Diktātā jāiekļauj vairāki vārdi,(49) kuru rakstība īpaši iegaumējama vai pārbaudāma ar konkrētiem likumiem,(50) pāris plaši izmantojamu svešvārdu,(51) vārda ‘lūzt’(52) formas.(53) Jā,(54) ar to laušanu un lūšanu visādi gājis.(55) Skolotāja skaitīja:(56) “(57)Vētrā visi koki lūst,(58) bet resnais ozols negrib lūzt.(59)”(60) Diez kas nav.(61)"));
        paragraphs.add(new Paragraph("Laikam prātīgāk būtu vispirms izveidot pašu tekstu,(62) cerot,(63) ka visus šos gramatiskos āķus iestrādāšu pēc tam.(64) Tagad tikai jāizdomā,(65) par ko rakstīt,(66) taču par to es domāšu rīt.(67)"));

        add(author, title, comments, paragraphs);
    }

    private void comments() {
        add(new H1("Komentāri"));
        add(new H2("Interpunkcija"));

        add(new H3("I rindkopa"));
        Div d1 = new Div();
        d1.add(new Paragraph("1. Komats atdala palīgteikumu. Komata vietā ir iespējama arī domuzīme, jo palīgteikumam paskaidrojuma nozīme pie “sajūtu”."));
        d1.add(new Paragraph("2. Punkts stāstījuma teikuma beigās."));
        d1.add(new Paragraph("3. Komats atdala izsauksmes vārdu."));
        d1.add(new Paragraph("4. Izsaukuma zīme vai daudzpunkte teikuma beigās."));
        d1.add(new Paragraph("5. Komats atdala vienlīdzīgus teikuma locekļus."));
        d1.add(new Paragraph("6. Punkts stāstījuma teikuma beigās."));
        d1.add(new Paragraph("7. Komats atdala divdabja teicienu."));
        d1.add(new Paragraph("8. Komats atdala vienlīdzīgus teikuma locekļus."));
        d1.add(new Paragraph("9. Komats atdala divdabja teicienu."));
        d1.add(new Paragraph("10. Punkts stāstījuma teikuma beigās."));
        add(d1);

        add(new H3("II rindkopa"));
        Div d2 = new Div();
        d2.add(new Paragraph("11. Komats atdala neatkarīgas teikuma daļas."));
        d2.add(new Paragraph("12. Komats atdala palīgteikumu."));
        d2.add(new Paragraph("13. Punkts stāstījuma teikuma beigās."));
        d2.add(new Paragraph("14. Punkts stāstījuma teikuma beigās."));
        d2.add(new Paragraph("15., 16. Komati atdala palīgteikumu. 15. komata vietā iespējama arī domuzīme, jo īpaši uzsvērta nosacījuma attieksme (sk. arī Blinkena 2009, 206)"));
        d2.add(new Paragraph("17. Punkts stāstījuma teikuma beigās."));
        d2.add(new Paragraph("18. Komats atdala neatkarīgas teikuma daļas."));
        d2.add(new Paragraph("19. Palīgteikumu drīkst atdalīt gan ar kolu, gan domuzīmi, gan komatu."));
        d2.add(new Paragraph("20. Punkts stāstījuma teikuma beigās."));
        add(d2);

        add(new H3("III rindkopa"));
        Div d3 = new Div();
        d3.add(new Paragraph("21. Komats atdala palīgteikumu."));
        d3.add(new Paragraph("22. Punkts stāstījuma teikuma beigās."));
        d3.add(new Paragraph("23. Komats atdala divdabja teicienu."));
        d3.add(new Paragraph("24. Komats atdala palīgteikumu."));
        d3.add(new Paragraph("25. Punkts stāstījuma teikuma beigās."));
        d3.add(new Paragraph("26. Komats atdala neatkarīgas teikuma daļas."));
        d3.add(new Paragraph("27. Punkts stāstījuma teikuma beigās."));
        d3.add(new Paragraph("28. Komats atdala neatkarīgas teikuma daļas. Kāds rakstītājs var iebilst, ka “tomēr” attiecas uz abām neatkarīgajām teikuma daļām un ka komatu nevajag, bet pēc nozīmes “tomēr” attiecas tikai uz pirmo daļu."));
        d3.add(new Paragraph("29., 30. Komati atdala divdabja teicienu."));
        d3.add(new Paragraph("31. Punkts stāstījuma teikuma beigās."));
        add(d3);

        add(new H3("IV rindkopa"));
        Div d4 = new Div();
        d4.add(new Paragraph("32. Komats atdala vienlīdzīgus teikuma locekļus."));
        d4.add(new Paragraph("33. Komats atdala vienlīdzīgus teikuma locekļus."));
        d4.add(new Paragraph("34. Komats atdala vienlīdzīgus teikuma locekļus."));
        d4.add(new Paragraph("35. Punkts stāstījuma teikuma beigās."));
        d4.add(new Paragraph("36. Komats atdala palīgteikumu."));
        d4.add(new Paragraph("37. Komats atdala palīgteikumu."));
        d4.add(new Paragraph("38. Punkts stāstījuma teikuma beigās."));
        d4.add(new Paragraph("39. Komats atdala vienlīdzīgus teikuma locekļus."));
        d4.add(new Paragraph("40. Komats atdala palīgteikumu."));
        d4.add(new Paragraph("41. Komats atdala palīgteikumu."));
        d4.add(new Paragraph("42. Punkts stāstījuma teikuma beigās."));
        d4.add(new Paragraph("43. Punkts stāstījuma teikuma beigās."));
        d4.add(new Paragraph("44. Komats atdala neatkarīgas teikuma daļas."));
        d4.add(new Paragraph("45. Jautājuma zīme jautājuma teikuma beigās."));
        add(d4);

        add(new H3("V rindkopa"));
        Div d5 = new Div();
        d5.add(new Paragraph("46. Komats atdala palīgteikumu."));
        d5.add(new Paragraph("47. Punkts stāstījuma teikuma beigās."));
        d5.add(new Paragraph("48. Punkts stāstījuma teikuma beigās. Pieļaujama izsaukuma zīme punkta vietā."));
        d5.add(new Paragraph("49., 50. Komati atdala palīgteikumu."));
        d5.add(new Paragraph("51. Komats atdala vienlīdzīgus teikuma locekļus."));
        d5.add(new Paragraph("52. Izceļams vārds, atdalāms ar pēdiņām vai vienpēdiņām."));
        d5.add(new Paragraph("53. Punkts stāstījuma teikuma beigās."));
        d5.add(new Paragraph("54. Komats atdala apgalvojuma partikulu."));
        d5.add(new Paragraph("55. Punkts stāstījuma teikuma beigās."));
        d5.add(new Paragraph("56. Kols pirms tiešās runas."));
        d5.add(new Paragraph("57., 60. Tiešā runa pēdiņās."));
        d5.add(new Paragraph("58. Komats atdala neatkarīgas teikuma daļas."));
        d5.add(new Paragraph("59. Punkts stāstījuma teikuma beigās.(Noteikti pirms pēdiņām)"));
        d5.add(new Paragraph("61. Punkts stāstījuma teikuma beigās."));
        add(d5);

        add(new H3("VI rindkopa"));
        Div d6 = new Div();
        d6.add(new Paragraph("62. Komats atdala divdabi, no kura atkarīgs palīgteikums."));
        d6.add(new Paragraph("63. Komats atdala palīgteikumu."));
        d6.add(new Paragraph("64. Punkts stāstījuma teikuma beigās."));
        d6.add(new Paragraph("65., 66. Komati atdala palīgteikumu."));
        d6.add(new Paragraph("67. Punkts stāstījuma teikuma beigās."));
        add(d6);

        add(new H2("Ortogrāfija"));

        add(new Paragraph("Ja nav uzrakstīts rakstnieka vārds – tā nav kļūda."));
        add(new Paragraph("Punktu aiz virsraksta nevērtējam."));
        add(new Paragraph("pasaules diktāts/Pasaules diktāts – abas formas ir pareizas."));
        add(new Paragraph("Skaitļa vārdu drīkst rakstīt gan ar vārdiem, gan cipariem – trīssimt/ 300 (bez punkta)."));
        add(new Paragraph("Prokrastinēt – svešvārda pareizrakstība"));
        add(new Paragraph("Droši vien – partikulas pareizrakstība"));
        add(new Paragraph("Tikai – partikulas pareizrakstība"));
        add(new Paragraph("It kā -  partikulas pareizrakstība"));
        add(new Paragraph("Vēl/vēlreiz – apstākļa vārdu pareizrakstība"));
        add(new Paragraph("Tūlīt – apstākļa vārda pareizrakstība."));
        add(new Paragraph(".. un kas tik vēl ne – katra atsevišķa vārda pareizrakstība."));
        add(new Paragraph("Ne tikvien – bet arī – saikļa pareizrakstība."));
        add(new Paragraph("Aizmirsdams – pārbaudes forma aizmirsa."));
        add(new Paragraph("Izskatās – jāievēro likums par a/ā darbības vārda tagadnes formās."));
        add(new Paragraph("Ceļas - jāievēro likums par a/ā darbības vārda tagadnes formās."));
        add(new Paragraph("Lūzt/lūst – nenoteiksmi pārbauda ar pagātnes formu lūza, bet tagadnē noticis vēsturiskais līdzskaņa z zudums pirms piedēkļa -st-."));
        add(new Paragraph("Lūšana – vēsturiskais līdzskaņa z zudums pirms izskaņas -šana."));
        add(new Paragraph("Laušana - vēsturiskais līdzskaņa z zudums pirms izskaņas -šana."));
        add(new Paragraph("Savlaicīgi – salikteņa pareizrakstība."));
        add(new Paragraph("Pēc tam – atsevišķi rakstāmi divi vārdi (prievārds un vietniekvārds)"));
    }




    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Optional<Pages> maybePages = pagesService.findById("original-text");
        if (maybePages.isPresent()) {
            Pages pages = maybePages.get();
            if (!pages.isPowerOn()) {
                beforeEnterEvent.forwardTo("about");
                Notification.show("Būs pieejams pēc diktāta", 7000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            }
        }
    }
}
