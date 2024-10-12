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
        author.setText("Aiva Kanepone");

        H2 title = new H2();
        title.setText("Putni brīnās par Liepāju");

        Div paragraphs = new Div();
        paragraphs.add(new Paragraph("Kurš liepājnieks gan nezina, ka Liepājā, Zirgu salā, ir putnu vērošanas tornis? Bet kurš ir pamanījis, ka arī putni vēro Liepāju? Brīnās, bola acis un groza galvas, un bezkaunīgi planē pāri kafejnīcu galdiņiem, lai ieskatītos nelidojošo divkāju šķīvjos. Liepājā ir daudz noslēpumaina, par ko brīnīties. Piemēram, naktīs, kad ne tikvien mēness piesprausts pie debesu samta kā zelta ananass, bet arī zvaigznes izbārstītas no Dieva atvilktnēm. Tad tur, lejā, zīriņiem par brīnumu, kaut kas iekvēlojas kā milzīgs ugunskurs, un tam viducī vītero kanārijputniņu bars. „Kas tas ir? Kas tas ir?” sakliedzas kaijas. Tām neviens nav izstāstījis par koncertzāli „Lielais dzintars” un simfonisko orķestri. Vēl viņi nesaprot, kāpēc jaukie Liepājas baznīcu torņi ik pa laikam sajūk prātā un pēkšņi sākas tāda dārdoņa, it kā tajos būtu ielauzušies kazuāri: „Bom! Bom! Bom!”"));
        paragraphs.add(new Paragraph("Un kas notiek Graudu ielas smalkāko namu augstumos? Tur nometas estēti baloži, kas dievina jūgendstilu. Ak, tie lieliskie izciļņi, kur dūdojot griezt mīlas dejas, un iedobumi, kur sastiept liepās salauztos zariņus minimālisma stilā ieturētajām ligzdām!"));
        paragraphs.add(new Paragraph("Kormorāni, viltnieki, jau trešo gadu perina bruņotu laupīšanas uzbrukumu Zivju tirgum Liepājas promenādē, tikai nevar sagaidīt, kad to atkal atvērs. Čurkstes slepeni skatās Liepājas teātra izrādes, bet pēc tam sajūsmā savidžinās. Bet zivju gārņi sapņo, garās kājas cilājot, pastaigāt pa Rožu laukuma dobēm. Diemžēl gārņi kautrējas. Toties vārnai Liepājā ir piemineklis. Karr!"));
        paragraphs.add(new Paragraph("Visskaistākajos vakaros, spārnus plivinādami, putni plecu pie pleca salaižas uz Ziemeļu mola vērot saulrietu. Kormorāns blakus paugurknābja zosij, cekulpīle blakus purva ķauķim, un pašā galā tārtiņš, no Līvu krasta atlaidies, klusiņām purpina lībiešu valodā."));
        paragraphs.add(new Paragraph("Rudenī viņi lūr pa Liepājas skolu logiem. Ieraugot gramatiskās kļūdas, saķer apaļās galveles un spalgi čiepst priekšā: „Stulbs nav stūlbs, albums nav albūms, mobilais nav nekāds mobīlais! Čiv!”"));
        paragraphs.add(new Paragraph("Klau, vai tagad arī uz palodzes negrabinās viena zilzīlīte? Ko, ko viņa tur čiepst?"));

        add(author, title, paragraphs);
    }

    private void textWithComments() {
        H1 author = new H1("Aiva Kanepone");
        H2 title = new H2("Putni brīnās par Liepāju");
        H3 comments = new H3("Teksts ar komentāriem");

        Div paragraphs = new Div();
        paragraphs.add(new Paragraph("Kurš liepājnieks gan nezina,(1) ka Liepājā,(2) Zirgu salā,(3) ir putnu vērošanas tornis?(4) Bet kurš ir pamanījis,(5) ka arī putni vēro Liepāju?(6) Brīnās,(7) bola acis un groza galvas,(8) un bezkaunīgi planē pāri kafejnīcu galdiņiem,(9) lai ieskatītos nelidojošo divkāju šķīvjos.(10) Liepājā ir daudz noslēpumaina,(11) par ko brīnīties.(12) Piemēram,(13) naktīs,(14) kad ne tikvien mēness piesprausts pie debesu samta kā zelta ananass,(15) bet arī zvaigznes izbārstītas no Dieva atvilktnēm.(16) Tad tur,(17) lejā,(18) zīriņiem par brīnumu,(19) kaut kas iekvēlojas kā milzīgs ugunskurs,(20) un tam viducī vītero kanārijputniņu bars.(21) „(22)Kas tas ir?(23) Kas tas ir?(24)”(25) sakliedzas kaijas.(26) Tām neviens nav izstāstījis par koncertzāli „(27)Lielais dzintars”(28) un simfonisko orķestri.(29) Vēl viņi nesaprot,(30) kāpēc jaukie Liepājas baznīcu torņi ik pa laikam sajūk prātā un pēkšņi sākas tāda dārdoņa,(31) it kā tajos būtu ielauzušies kazuāri:(32) „(33)Bom!(34) Bom!(35) Bom!(36)”(37)"));
        paragraphs.add(new Paragraph("Un kas notiek Graudu ielas smalkāko namu augstumos?(38) Tur nometas estēti baloži,(39) kas dievina jūgendstilu.(40) Ak,(41) tie lieliskie izciļņi,(42) kur dūdojot griezt mīlas dejas,(43) un iedobumi,(44) kur sastiept liepās salauztos zariņus minimālisma stilā ieturētajām ligzdām!(45)"));
        paragraphs.add(new Paragraph("Kormorāni,(46) viltnieki,(47) jau trešo gadu perina bruņotu laupīšanas uzbrukumu Zivju tirgum Liepājas promenādē,(48) tikai nevar sagaidīt(49), kad to atkal atvērs.(50) Čurkstes slepeni skatās Liepājas teātra izrādes,(51) bet pēc tam sajūsmā savidžinās.(52) Bet zivju gārņi sapņo,(53) garās kājas cilājot,(54) pastaigāt pa Rožu laukuma dobēm.(55) Diemžēl gārņi kautrējas.(56) Toties vārnai Liepājā ir piemineklis.(57) Karr!(58)"));
        paragraphs.add(new Paragraph("Visskaistākajos vakaros,(59) spārnus plivinādami,(60) putni plecu pie pleca salaižas uz Ziemeļu mola vērot saulrietu.(61) Kormorāns blakus paugurknābja zosij,(62) cekulpīle blakus purva ķauķim,(63) un pašā galā tārtiņš,(64) no Līvu krasta atlaidies,(65) klusiņām purpina lībiešu valodā.(66)"));
        paragraphs.add(new Paragraph("Rudenī viņi lūr pa Liepājas skolu logiem.(67) Ieraugot gramatiskās kļūdas,(68) saķer apaļās galveles un spalgi čiepst priekšā:(69) „(70)Stulbs nav stūlbs,(71) albums nav albūms,(72) mobilais nav nekāds mobīlais!(73) Čiv!(74)”(75)"));
        paragraphs.add(new Paragraph("Klau,(76) vai tagad arī uz palodzes negrabinās viena zilzīlīte?(77) Ko,(78) ko viņa tur čiepst?(79)"));

        add(author, title, comments, paragraphs);
    }

    private void comments() {
        add(new H1("Komentāri"));
        add(new H2("Ortogrāfija"));

        add(new Paragraph("Zirgu sala, Zivju tirgus, Rožu laukums, Ziemeļu mols, Līvu krasts \n" +
                "ananass (sic!)"));
        add(new Paragraph("atvilktne"));

        add(new H2("Interpunkcija"));
        add(new H3("I rindkopa"));
        Div d1 = new Div();
        d1.add("1. Sākas palīgteikums");
        d1.add("2., 3. Atdala savrupinājumu. Fakultatīvi komati (Blinkena 290.lpp.)");
        d1.add("4. Jautājuma teikums");
        d1.add("5. Sākas palīgteikums");
        d1.add("6. Jautājuma teikums");
        d1.add("7., 8. Vienlīdzīgi teikuma locekļi");
        d1.add("9. Sākas palīgteikums");
        d1.add("10. Stāstījuma teikums");
        d1.add("11. Sākas palīgteikums");
        d1.add("12. Stāstījuma teikums");
        d1.add("13. Iespraudums");
        d1.add("14. Sākas palīgteikums");
        d1.add("15. Vienlīdzīgi palīgteikumi");
        d1.add("16. Stāstījuma teikums");
        d1.add("17., 18. Savrupinājums. Fakultatīvi komati (Blinkena 290.lpp.)");
        d1.add("19. Beidzas iespraudums. Var paredzēt fakultatīvu pieturzīmi (Blinkena 257.lpp.)");
        d1.add("20. Salikta teikuma daļa. Cilvēki var interpretēt, ka sākuma laika apstāklis “Tad” attiecas uz abām teikuma daļām. Tādā gadījumā komatu neliek");
        d1.add("21. Stāstījuma teikums");
        d1.add("22., 25. Tiešā runa");
        d1.add("23., 24. Jautājuma teikumi");
        d1.add("26. Stāstījuma teikums");
        d1.add("27., 28. Nosaukums ar īpašvārdisku nozīmi pēdiņās");
        d1.add("29. Stāstījuma teikums");
        d1.add("30. Sākas palīgteikums");
        d1.add("31. Sākas palīgteikums");
        d1.add("32. Kols pirms tiešās runas");
        d1.add("33., 37. Tiešā runa pēdiņās");
        d1.add("34., 35., 36. BS: Izsaukuma teikumi. (Blinkena 347.lpp.)");
        add(d1);

        add(new H3("II rindkopa"));
        Div d2 = new Div();
        d2.add("38. Jautājuma teikums");
        d2.add("39. Sākas palīgteikums");
        d2.add("40. Stāstījuma teikums");
        d2.add("41. Atdalīts  izsauksmes vārds");
        d2.add("42., 43. Atdala palīgteikumu");
        d2.add("44. Sākas palīgteikums");
        d2.add("45. Izsaukuma teikuma beigas");
        add(d2);

        add(new H3("III rindkopa"));
        Div d3 = new Div();
        d3.add("46., 47. Atdalīts savrupinājums");
        d3.add("48. Atdala vienlīdzīgus teikuma locekļus");
        d3.add("49. Sākas palīgteikums");
        d3.add("50. Stāstījuma teikums");
        d3.add("51. Vienlīdzīgi teikuma locekļi");
        d3.add("52. Stāstījuma teikums");
        d3.add("53., 54. Atdala divdabja teicienu");
        d3.add("55. Stāstījuma teikums");
        d3.add("56. Stāstījuma teikums");
        d3.add("57. Stāstījuma teikums");
        d3.add("58. Izsaukuma teikums");
        add(d3);

        add(new H3("IV rindkopa"));
        Div d4 = new Div();
        d4.add("59., 60. Atdala divdabja teicienu");
        d4.add("61. Stāstījuma teikums");
        d4.add("62. Salikta teikuma daļa. Var paredzēt fakultatīvu domuzīmi");
        d4.add("63. Salikta teikuma daļa");
        d4.add("64., 65. Atdala divdabja teicienu");
        d4.add("66. Stāstījuma teikums");
        add(d4);

        add(new H3("V rindkopa"));
        Div d5 = new Div();
        d5.add("67. Stāstījuma teikums");
        d5.add("68. Atdala divdabja teicienu");
        d5.add("69. Kols aiz piebildes");
        d5.add("70., 75. Tiešā runa pēdiņās");
        d5.add("71. Teikuma daļas");
        d5.add("72. Teikuma daļas");
        d5.add("73. Izsaukuma teikums");
        d5.add("74. Izsaukuma teikums");
        add(d5);

        add(new H3("VI rindkopa"));
        Div d6 = new Div();
        d6.add("76. Izsauksmes vārds");
        d6.add("77. Jautājuma teikums");
        d6.add("78. Atkārtoti teikuma locekļi");
        d6.add("79. Jautājuma teikums");
        add(d6);

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
