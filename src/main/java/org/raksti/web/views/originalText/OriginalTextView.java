package org.raksti.web.views.originalText;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
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

@PageTitle("Oriģināls tekst")
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

        HtmlComponent br = new HtmlComponent("br");

        Div author = new Div();
        author.setText("Osvalds Zebris");

        Div title = new Div();
        title.setText("Krāsaina saule virs pelēkiem jumtiem");

        Div paragraphOne = new Div();
        paragraphOne.setText("Visbrīnišķīgākais ir tas, ka viņa jau paspējusi pacelties un nu mirdz visā krāšņumā. " +
                "Džeimss ir iznācis ārā tieši rītausmā, lai pieredzētu cilvēku reakciju. Pilsēta mostas negribīgi – skatītāju ir krietni mazāk, nekā viņš cerēja. " +
                "Vien dāma baltā mētelī, sētnieks  un atkritumu auto.");

        Div paragraphTwo = new Div();
        paragraphTwo.setText("\"Tā ir manis radīta…\" " +
                "sakāmo nepabeidzis, Džeimss paceļ augšup rādītājpirkstu, it kā viņš jau atkal savas klases priekšā skaidrotu kādu uz tāfeles uzrakstītu vārdu. Viņa klase ir sen izaugusi no hūdijiem un gotu zābakiem, savukārt tāfeles tagad atrodamas muzejā. Džeimsa audzēkņi ir aizgājuši savus ceļus, bet viņš – reiz mīlētais skolotājs – kļuvis aizmāršīgs un vēl aizdomīgāks pret visu svešādo. Un nu tieši viņš būs radījis šo brīnumu, kas mirdz iepretim presbiteriešu baznīcas tornim, norādot uz debesīm gluži kā tāds večuka pirksts. " +
                "Atkritumu vedējs dūc kā bišu spiets, tad apstājas ielas vidū, jo šoferis beidzot būs pamanījis Džeimsa sauli.");

        Div paragraphThree = new Div();
        paragraphThree.setText("\"Tīrākā ambivalence,\" Džeimsam līdzās nostājas dāma baltā, \"vai saprotat?\"");

        Div paragraphFour = new Div();
        paragraphFour.setText("\"Jo vecāks, jo mazāk es ko saprotu…\" Džeimsam aizraujas elpa, tāpēc ka parādība uzņem ātrumu, rotējot ap savu asi. " +
                "Daudzkrāsaini stari vērpjas, aizķerot namu sienas un aptinot zvanu torni, un zvans dobji ieīdas.");

        Div paragraphFive = new Div();
        paragraphFive.setText("\"Lūsti taču beidzot!\" čukst Džeimss, kuram baznīca vienmēr šķitusi kaitīga šai minimālisma arhitektūrā ieturētajai pilsētas daļai. " +
                "Dziļas saknes laidusī celtne pat nesakustas. Plēsdams nost sludinājumus pieturā, sētnieks apliecina, ka brīnumi viņu neskar.");

        Div paragraphSix = new Div();
        paragraphSix.setText("Džeimss aizmiedz acis, jo zina: tiklīdz atvērs, visi metaversa māņi būs izzuduši. " +
                "Paliks vien pelēka pilsēta, kurā brīnumi nenotiek, tie nav vajadzīgi.");

        Div paragraphSeven = new Div();
        paragraphSeven.setText("Kad Džeimss ir izmēģinājis viņa klases dāvāto virtuālās realitātes ķiveri, viņš saprot: no šī āķa vairs nenolēks. Retajos brīžos viņš izraujas no sintētiskās vides un atskārš, cik nejēdzīga ir kļuvusi reālā, jo robeža starp divām pasaulēm nupat izgaisusi. " +
                "Ne tikvien dāma, sētnieks, atkritumu vedējs, bet arī viņš pats saullēktā var piederēt abām pasaulēm.");

        Div paragraphEight = new Div();
        paragraphEight.setText("Džeimss paver plakstiņus, dziļi ieelpo un sajūt bērnības pavasaru smaržu, kad viņš klunkurēja uz skolu un sapņoja par krāsainu sauli virs pelēkiem jumtiem.");

        add(author, br, title, br, paragraphOne, br, paragraphTwo, br, paragraphThree, br,
                paragraphFour, br, paragraphFive, br, paragraphSix, br,
                paragraphSeven, br, paragraphEight);
    }

    private void textWithComments() {
        HtmlComponent br = new HtmlComponent("br");

        Div author = new Div();
        author.setText("Osvalds Zebris");
        author.getStyle().set("font-weight", "bold");

        Div title = new Div();
        title.setText("Krāsaina saule virs pelēkiem jumtiem");
        title.getStyle().set("font-weight", "bold");

        Div paragraphOne = new Div();
        paragraphOne.setText("Visbrīnišķīgākais ir tas,(1) ka viņa jau paspējusi pacelties un nu mirdz visā krāšņumā.(2) " +
                "Džeimss ir iznācis ārā tieši rītausmā,(3) lai pieredzētu cilvēku reakciju.(4) Pilsēta mostas negribīgi – (5)skatītāju ir krietni mazāk,(6) nekā viņš cerēja.(7) " +
                "Vien dāma baltā mētelī,(8) sētnieks  un atkritumu auto.(9)");

        Div paragraphTwo = new Div();
        paragraphTwo.setText("\"(10)Tā ir manis radīta…(11)\"(12) sakāmo nepabeidzis,(13) Džeimss paceļ augšup rādītājpirkstu,(14) it kā viņš jau atkal savas klases priekšā skaidrotu kādu uz tāfeles uzrakstītu vārdu.(15) " +
                "Viņa klase ir sen izaugusi no hūdijiem un gotu zābakiem,(16) savukārt tāfeles tagad atrodamas muzejā.(17) Džeimsa audzēkņi ir aizgājuši savus ceļus,(18) bet viņš –(19) reiz mīlētais skolotājs – (20) kļuvis aizmāršīgs un vēl aizdomīgāks pret visu svešādo.(21) " +
                "Un nu tieši viņš būs radījis šo brīnumu,(22) kas mirdz iepretim presbiteriešu baznīcas tornim,(23) norādot uz debesīm gluži kā tāds večuka pirksts.(24) Atkritumu vedējs dūc kā bišu spiets,(25) tad apstājas ielas vidū,(26) jo šoferis beidzot būs pamanījis Džeimsa sauli.(27)");

        Div paragraphThree = new Div();
        paragraphThree.setText("\"(28)Tīrākā ambivalence,(29)\"(30) Džeimsam līdzās nostājas dāma baltā,(31) \"(32)vai saprotat?(33)\"(34)");

        Div paragraphFour = new Div();
        paragraphFour.setText("\"(35)Jo vecāks,(36) jo mazāk es ko saprotu…(37)\"(38) Džeimsam aizraujas elpa,(39) tāpēc ka parādība uzņem ātrumu,(40) rotējot ap savu asi." +
                "(41)Daudzkrāsaini stari vērpjas,(42) aizķerot namu sienas un aptinot zvanu torni,(43) un zvans dobji ieīdas.(44)");

        Div paragraphFive = new Div();
        paragraphFive.setText("\"(45)Lūsti taču beidzot!(46)\"(47) čukst Džeimss,(48) kuram baznīca vienmēr šķitusi kaitīga šai minimālisma arhitektūrā ieturētajai pilsētas daļai.(49) Dziļas saknes laidusī celtne pat nesakustas." +
                "(50) Plēsdams nost sludinājumus pieturā,(51) sētnieks apliecina,(52) ka brīnumi viņu neskar.(53");

        Div paragraphSix = new Div();
        paragraphSix.setText("Džeimss aizmiedz acis,(54) jo zina:(55) tiklīdz atvērs,(56) visi metaversa māņi būs izzuduši." +
                "(57) Paliks vien pelēka pilsēta,(58) kurā brīnumi nenotiek,(59) tie nav vajadzīgi.(60)");

        Div paragraphSeven = new Div();
        paragraphSeven.setText("Kad Džeimss ir izmēģinājis viņa klases dāvāto virtuālās realitātes ķiveri,(61) viņš saprot:(62) no šī āķa vairs nenolēks.(63) Retajos brīžos viņš izraujas no sintētiskās vides un atskārš,(64) cik nejēdzīga ir kļuvusi reālā,(65) jo robeža starp divām pasaulēm nupat izgaisusi." +
                "(66) Ne tikvien dāma,(67) sētnieks,(68) atkritumu vedējs,(69) bet arī viņš pats saullēktā var  piederēt abām pasaulēm.(70) ");

        Div paragraphEight = new Div();
        paragraphEight.setText("Džeimss paver plakstiņus,(71) dziļi ieelpo un sajūt bērnības pavasaru smaržu,(72) kad viņš klunkurēja uz skolu un sapņoja par krāsainu sauli virs pelēkiem jumtiem.(73)");

        add(author, br, title, br, paragraphOne, br, paragraphTwo, br, paragraphThree, br,
                paragraphFour, br, paragraphFive, br, paragraphSix, br,
                paragraphSeven, br, paragraphEight);
    }

    private void comments() {
        HtmlComponent br = new HtmlComponent("br");

        Div commentsTitle = new Div();
        commentsTitle.setText("Komentāri");
        commentsTitle.getStyle().set("text-decoration", "underline");
        commentsTitle.getStyle().set("font-weight", "bold");

        Div orthographyTitle = new Div();
        orthographyTitle.setText("Ortogrāfija");
        orthographyTitle.getStyle().set("font-weight", "bold");

        Div anglicisms = new Div();
        anglicisms.add(new Paragraph("Džeimss ar diviem s, jo tekstā ir vārdformas Džeimsa, Džeimsam"),
                new Paragraph("Hūdijs – svīteris ar kapuci. Angļu val. hoodie, kam pamatā hood ‘kapuce’"),
                new Paragraph("Presbiteriešu (autors izmantojis novec. vārdu), variants prezbiteriāņu"),
                new Paragraph("Lūsti – sakne lū-, piedēklis -st-, galotne -i"),
                new Paragraph("Plēsdams – pārb. plēsa"),
                new Paragraph("Metaverss – virtuālās realitātes telpa, kurā lietotāji var mijiedarboties ar virtuālo vidi un citiem"),
                new Paragraph("lietotājiem. Meta ‘kaut kas ārpasaulīgs, ārpus izziņas robežām esošs’ un universe ‘visums’."),
                new Paragraph("Jāievēro saikļi tiklīdz; tāpēc ka; ne tikvien – bet arī"));

        Div punctuationTitle = new Div();
        punctuationTitle.getStyle().set("font-weight", "bold");
        punctuationTitle.setText("Interpunkcija");

        Div underPunctuationTitle = new Div();
        underPunctuationTitle.setText("Punktu aiz virsraksta nevērtē.");

        Div paragraphOneTitle = new Div(new Paragraph("I rindkopa"));
        paragraphOneTitle.getStyle().set("text-align", "center");
        paragraphOneTitle.getStyle().set("width", "100%");

        Div paragraphOne = new Div();
        paragraphOne.add(new Paragraph(),
                new Paragraph("1.\tKomats atdala palīgteikumu."),
                new Paragraph("2.\tPunkts stāstījuma teikuma beigās."),
                new Paragraph("3.\tKomats atdala palīgteikumu."),
                new Paragraph("4.\tPunkts stāstījuma teikuma beigās."),
                new Paragraph("5.\tDomuzīme. Domuzīme, mūsuprāt, ir visiederīgākā (tur var būt vai nu divas neatkarīgas daļas, vai virsteikuma un palīgteikuma robeža), bet var būt arī “:”, sk.: Blinkena 168.lpp, 4.; Blinkena 171., 172. lpp. augšā. " +
                        "Man šķiet, ka labāk paredzēt variantus."),
                new Paragraph("6.\tKomats atdala palīgteikumu."),
                new Paragraph("7.\tPunkts stāstījuma teikuma beigās."),
                new Paragraph("8.\tKomats atdala vienlīdzīgus teikuma locekļus"),
                new Paragraph("9.\tPunkts stāstījuma teikuma beigās."));

        Div paragraphTwoTitle = new Div(new Paragraph("II rindkopa"));
        paragraphTwoTitle.getStyle().set("text-align", "center");
        paragraphTwoTitle.getStyle().set("width", "100%");

        Div paragraphTwo = new Div();
        paragraphTwo.add(new Paragraph("10., 12. Tiešā runa pēdiņās."),
                new Paragraph("11.  Daudzpunkte, jo doma nav pabeigta."),
                new Paragraph("13. Komats atdala divdabja teicienu"),
                new Paragraph("14. Komats atdala palīgteikumu."),
                new Paragraph("15. Punkts stāstījuma teikuma beigās."),
                new Paragraph("16. Komats atdala salikta sakārtota teikuma daļas."),
                new Paragraph("17. Punkts stāstījuma teikuma beigās"),
                new Paragraph("18. Komats atdala salikta sakārtota teikuma daļas."),
                new Paragraph("19., 20. Domuzīmes (vai komati) atdala savrupinātu pielikuma grupu."),
                new Paragraph("21. Punkts stāstījuma teikuma beigās."),
                new Paragraph("22. Komats atdala palīgteikumu."),
                new Paragraph("23. Komats atdala divdabja teicienu."),
                new Paragraph("24. Punkts stāstījuma teikuma beigās."),
                new Paragraph("25. Komats atdala vienlīdzīgus teikuma locekļus."),
                new Paragraph("26. Komats atdala palīgteikumu."),
                new Paragraph("27. Punkts stāstījuma teikuma beigās."));

        Div paragraphThreeTitle = new Div(new Paragraph("III rindkopa"));
        paragraphThreeTitle.getStyle().set("text-align", "center");
        paragraphThreeTitle.getStyle().set("width", "100%");

        Div paragraphThree = new Div(new Paragraph("28., 30., 32., 34. Pēdiņas norāda tiešo runu."),
                new Paragraph("29. Komats tiešās runas pārtraukumā pirms pēdiņām."),
                new Paragraph("31. Komats aiz piebildes, kas atrodas starp tiešās runas vārdiem."),
                new Paragraph("Tiek pieņemts variants ar punktu un nākamo tiešo runu kā jaunu teikumu."));

        Div paragraphFourTitle = new Div(new Paragraph("IV rindkopa"));
        paragraphFourTitle.getStyle().set("text-align", "center");
        paragraphFourTitle.getStyle().set("width", "100%");

        Div paragraphFour = new Div(new Paragraph("35., 38. Pēdiņas norāda tiešo runu."),
                new Paragraph("36. Komats atdala palīgteikumu"),
                new Paragraph("37. Komats vai daudzpunkte aiz tiešās runas pirms pēdiņām."),
                new Paragraph("39. Komats atdala palīgteikumu. Loģisks ir saiklis tāpēc ka, bet iespējams arī variants tāpēc, ka. Arī Blinkenas grāmatā (184.–185.) nianse pamatā balstās uz intonāciju."),
                new Paragraph("40. Komats atdala divdabja teicienu."),
                new Paragraph("41. Punkts stāstījuma teikuma beigās."),
                new Paragraph("42. Komats atdala divus vienlīdzīgus divdabja teicienus"),
                new Paragraph("43. Komats atdala neatkarīgu teikuma daļu. "),
                new Paragraph("44. Punkts stāstījuma teikuma beigās."));

        Div paragraphFiveTitle = new Div(new Paragraph("V rindkopa"));
        paragraphFiveTitle.getStyle().set("text-align", "center");
        paragraphFiveTitle.getStyle().set("width", "100%");

        Div paragraphFive = new Div(new Paragraph("45., 47. Pēdiņas norāda tiešo runu."),
                new Paragraph("46. Izsaukuma zīme tiešās runas beigās pirms pēdiņām."),
                new Paragraph("48. Komats atdala palīgteikumu."),
                new Paragraph("49.  Punkts stāstījuma teikuma beigās."),
                new Paragraph("50. Punkts stāstījuma teikuma beigās."),
                new Paragraph("51. Komats atdala divdabja teicienu."),
                new Paragraph("52. Komats atdala palīgteikumu"),
                new Paragraph("53.Punkts stāstījuma teikuma beigās"));

        Div paragraphSixTitle = new Div(new Paragraph("VI rindkopa"));
        paragraphSixTitle.getStyle().set("text-align", "center");
        paragraphSixTitle.getStyle().set("width", "100%");

        Div paragraphSix = new Div(new Paragraph("54. Komats atdala palīgteikumu."),
                new Paragraph("55. Kols vai domuzīme atdala palīgteikumu bezsaikļa saistījumā. Blinkena 170. lpp., 41. paragr., 1. un 167. lpp., 39. paragr. 3"),
                new Paragraph("56. Komats atdala palīgteikumu."),
                new Paragraph("57.\tPunkts stāstījuma teikuma beigās."),
                new Paragraph("58.,59.Komati atdala palīgteikumu. Kāds tur var iedomāties arī pakāpenisku palīgteikumu pakārtojumu, kur izlaists pamatojuma palīgteikuma saiklis (..kurā nenotiek [jo] tie nav vajadzīgi…). Tad tur atkal būtu pieļaujams “:” vai “–“. "));

        Div paragraphSevenTitle = new Div(new Paragraph("VII rindkopa"));
        paragraphSevenTitle.getStyle().set("text-align", "center");
        paragraphSevenTitle.getStyle().set("width", "100%");

        Div paragraphSeven = new Div(new Paragraph("60.\tKomats atdala palīgteikumu."),
                new Paragraph("61.\tKols vai domuzīme atdala palīgteikumu bezsaikļa saistījumā. "),
                new Paragraph("62.\tPunkts stāstījuma teikuma beigās."),
                new Paragraph("63.\tKomats atdala palīgteikumu."),
                new Paragraph("64.\tKomats atdala palīgteikumu."),
                new Paragraph("65.\tPunkts stāstījuma teikuma beigās."),
                new Paragraph("67., 68., 69. atdala vienlīdzīgus teikuma locekļus."),
                new Paragraph("70. Punkts stāstījuma teikuma beigās."));

        Div paragraphEightTitle = new Div(new Paragraph("VIII rindkopa"));
        paragraphEightTitle.getStyle().set("text-align", "center");
        paragraphEightTitle.getStyle().set("width", "100%");

        Div paragraphEight = new Div(new Paragraph("71. Komats atdala vienlīdzīgus teikuma locekļus."),
                new Paragraph("72. Komats atdala palīgteikumu."),
                new Paragraph("73.\tPunkts stāstījuma teikuma beigās."));


        add(commentsTitle, orthographyTitle, br, anglicisms, br, br, paragraphOneTitle, paragraphOne, paragraphTwoTitle, paragraphTwo,
                paragraphThreeTitle, paragraphThree, paragraphFourTitle, paragraphFour, paragraphFiveTitle, paragraphFive,
                paragraphSixTitle, paragraphSix, paragraphSevenTitle, paragraphSeven, paragraphEightTitle, paragraphEight
                );
    }




    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Optional<Pages> maybePages = pagesService.findById("original-text");
        if (maybePages.isPresent()) {
            Pages pages = maybePages.get();
            if (!pages.isPowerOn()) {
                beforeEnterEvent.forwardTo("about");
                Notification.show("Būs pieejāms pēc diktāta", 7000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_PRIMARY);
            }
        }
    }
}
