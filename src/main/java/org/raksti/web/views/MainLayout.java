package org.raksti.web.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.Pages;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.PagesService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.about.CreateAboutView;
import org.raksti.web.views.allExams.CreatedExams;
import org.raksti.web.views.buj.CreateFAQView;
import org.raksti.web.views.buj.FAQView;
import org.raksti.web.views.createUser.CreateUser;
import org.raksti.web.views.donation.CreateDonationView;
import org.raksti.web.views.donation.DonationView;
import org.raksti.web.views.edit.EditView;
import org.raksti.web.views.examResults.ExamResultsView;
import org.raksti.web.views.listofparticipants.ListofparticipantsView;
import org.raksti.web.views.newExam.ExamsView;
import org.raksti.web.views.offlineLocation.OfflineLocationParticipantsView;
import org.raksti.web.views.offlineLocation.OfflineLocationView;
import org.raksti.web.views.originalText.OffOnOriginalText;
import org.raksti.web.views.originalText.OriginalTextView;
import org.raksti.web.views.participateInExam.ParticipateInExamView;
import org.raksti.web.views.profile.ProfileView;
import org.raksti.web.views.sendEmailsPage.EmailSenderView;
import org.raksti.web.views.sponsors.CreateSponsorView;
import org.raksti.web.views.sponsors.SponsorView;
import org.raksti.web.views.userExamHistory.UserExamHistoryView;

import java.util.Optional;

public class MainLayout extends AppLayout {

    public static class MenuItemInfo extends ListItem {
        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            link.addClassNames("flex", "gap-xs", "h-m", "items-center", "px-s", "text-body");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            text.addClassNames("font-medium", "text-m", "whitespace-nowrap");
            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                addClassNames("text-l", "text-secondary");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }
    private final AccessAnnotationChecker accessChecker;
    private final AuthenticatedUser authenticatedUser;
    private final PagesService pagesService;

    public MainLayout(@NotNull AuthenticatedUser user, @NotNull AccessAnnotationChecker accessChecker, @NotNull PagesService pagesService) {
        this.accessChecker = accessChecker;
        this.authenticatedUser = user;
        this.pagesService = pagesService;
        addToNavbar(createHeaderContent());
    }

    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames("box-border", "flex", "flex-col", "w-full");

        HorizontalLayout hl = new HorizontalLayout();
        hl.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        hl.getStyle().set("padding-right", "10px");
        hl.getStyle().set("width", "20%");
        hl.getStyle().set("flex-wrap", "wrap");
        hl.setAlignItems(FlexComponent.Alignment.CENTER);

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isEmpty()) {
            Button reg = new Button("Reģistrēties");
            Button log = new Button("Pieslēgties");
            reg.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("registration")));
            log.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("login")));
            hl.add(reg, log);
        } else {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getFirstName() + " " + user.getLastName(), user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");
            avatar.setColorIndex(2);

            Button logout = new Button("Iziet");
            logout.addClickListener(e -> authenticatedUser.logout());

            Span name = new Span(user.getFirstName() + " " + user.getLastName());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            hl.add(avatar, name, logout);
        }

        Div layout = new Div();
        layout.addClassNames("flex", "items-center", "px-l");
        Image image = new Image("images/logo.png", "logo");
        image.setHeight("80px");
        image.setWidth("80px");
        Anchor link = new Anchor("/");
        link.add(image);

        layout.add(link);

        Nav nav = new Nav();
        nav.addClassNames("flex", "overflow-auto", "px-m", "py-xs");

        HorizontalLayout list = new HorizontalLayout();
        list.addClassNames("flex", "gap-s", "list-none", "m-0", "p-0");
        list.getStyle().set("width", "80%");
        list.getStyle().set("flex-wrap", "wrap");
        list.getStyle().set("align-items", "center");
        nav.add(layout, list, hl);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }
        }

        Optional<Pages> offlinePage = pagesService.findById(OffOnOriginalText.OFFLINE_PAGE);
        boolean offlinePageEnabled = offlinePage.isPresent() && offlinePage.get().isPowerOn();
        if (offlinePageEnabled) {
            MenuItemInfo offline;
            if (maybeUser.isPresent()) {
                offline = new MenuItemInfo("Pieteikties rakstīšanai klātienē", "la la-file-text", OfflineLocationView.class);
                list.add(offline);
            }
        }

        header.add(nav);
        return header;
    }

//    @NotNull
//    private MenuItemInfo getOfflineRegistrationMenuItem() {
//        MenuItemInfo offline = new MenuItemInfo("Pieteikties rakstīšanai klātienē", "la la-file-text", AboutView.class);
//        offline.addClickListener(e -> {
//            Dialog dialog = new Dialog();
//            dialog.setCloseOnOutsideClick(true);
//            dialog.setHeaderTitle("Piesakieties vai reģistrējieties");
//
//            VerticalLayout dialogLayout = new VerticalLayout();
//            dialogLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//
//            Button log = new Button("Pieslēgties");
//            log.getStyle().set("width", "300px");
//            Button reg = new Button("Reģistrēties");
//            reg.getStyle().set("width", "300px");
//
//            log.addClickListener(event -> {
//                getUI().ifPresent(ui -> ui.navigate("login"));
//                dialog.close();
//            });
//            reg.addClickListener(event -> {
//                getUI().ifPresent(ui -> ui.navigate("registration"));
//                dialog.close();
//            });
//
//            dialogLayout.add(log, reg);
//
//            dialog.add(dialogLayout);
//
//            dialog.open();
//        });
//        return offline;
//    }


    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Profils", "la la-globe", ProfileView.class),

                new MenuItemInfo("BUJ", "la la-weixin", FAQView.class),

                new MenuItemInfo("Atbalsts un saziņa", "la la-group", DonationView.class),

                new MenuItemInfo("Par iniciatīvu", "la la-bell", SponsorView.class),

//                new MenuItemInfo("Instrukcija", "", InstructionView.class),

                new MenuItemInfo("Lietotāju saraksts", "la la-columns", ListofparticipantsView.class),

                new MenuItemInfo("Piedalīties diktātā tiešsaistē", "la la-edit", ParticipateInExamView.class),

                new MenuItemInfo("Diktāta oriģinālteksts", "la la-file-text", OriginalTextView.class),

                new MenuItemInfo("Sūtīt svarīgu ziņojumu", "la la-edit", EditView.class),

                new MenuItemInfo("Izveidot jaunu adminu/lietotāju", "la la-pencil", CreateUser.class),

                new MenuItemInfo("Diktāts", "la la-file-text-o", ExamsView.class),

                new MenuItemInfo("Manu diktātu vēsture", "la la-eye", UserExamHistoryView.class),

                new MenuItemInfo("Diktātu rezultāti", "la la-certificate", ExamResultsView.class),

                new MenuItemInfo("Izveidotie diktāti", "la la-file-text", CreatedExams.class),

                new MenuItemInfo("Sūtīt vēstules", "la la-file", EmailSenderView.class),

                new MenuItemInfo("Klātienes diktāta dalībnieki", "la la-file-text", OfflineLocationParticipantsView.class),

                new MenuItemInfo("BUJ rediģēt", "la la-edit", CreateFAQView.class),

                new MenuItemInfo("Sākumlapa rediģēt", "la la-edit", CreateAboutView.class),

                new MenuItemInfo("Par iniciatīvu rediģēt", "la la-edit", CreateSponsorView.class),

                new MenuItemInfo("Atbalsts un saziņa rediģēt", "la la-edit", CreateDonationView.class),

                new MenuItemInfo("Izslēgt/ieslēgt lapas", "la la-edit", OffOnOriginalText.class)

                //
        };
    }

}
