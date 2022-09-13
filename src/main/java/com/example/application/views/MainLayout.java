package com.example.application.views;

import com.example.application.data.entity.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.about.AboutView;
import com.example.application.views.allExams.CreatedExams;
import com.example.application.views.createUser.CreateUser;
import com.example.application.views.edit.EditView;
import com.example.application.views.examResults.ExamResultsView;
import com.example.application.views.listofparticipants.ListofparticipantsView;
import com.example.application.views.newExam.ExamsView;
import com.example.application.views.offlineExam.CreateNewExamForm;
import com.example.application.views.offlineExam.ParticipantsView;
import com.example.application.views.offlineExam.OfflineExamsView;
import com.example.application.views.participateInExam.ParticipateInExamView;
import com.example.application.views.profile.ProfileView;
import com.example.application.views.userExamHistory.UserExamHistoryView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            link.addClassNames("menu-item-link");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            text.addClassNames("menu-item-text");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);

        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                addClassNames("menu-item-icon");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }

    private H1 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassNames("view-toggle");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("view-title");

        Header header = new Header(toggle, viewTitle);
        header.addClassNames("view-header");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Raksti");
        appName.addClassNames("app-name");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("drawer-section");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("menu-item-container");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("navigation-list");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }

        }
        return nav;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Profils", "la la-globe", ProfileView.class), //

                new MenuItemInfo("Sākumlapa", "la la-file", AboutView.class), //

                new MenuItemInfo("Lietotāju saraksts", "la la-columns", ListofparticipantsView.class),

                new MenuItemInfo("Rediģēt sākumlapu", "la la-edit", EditView.class),

                new MenuItemInfo("Izveidot jaunu adminu", "la la-pencil", CreateUser.class),

                new MenuItemInfo("Diktāts", "la la-file-text-o", ExamsView.class),

                new MenuItemInfo("Piedalīties diktātā tiešsaistē", "la la-edit", ParticipateInExamView.class),

                new MenuItemInfo("Manu diktātu vēsture", "la la-eye", UserExamHistoryView.class),

                new MenuItemInfo("Diktātu rezultāti", "la la-certificate", ExamResultsView.class),

                new MenuItemInfo("Izveidotie diktāti", "la la-file-text", CreatedExams.class),
                new MenuItemInfo("Create New Offline Exam", "la la-file-text", CreateNewExamForm.class),
                new MenuItemInfo("Offline Exams", "la la-file-text", OfflineExamsView.class),
                new MenuItemInfo("Participants", "la la-file-text", ParticipantsView.class)
                //

        };
    }

    private HorizontalLayout createFooter() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.addClassNames("footer");
        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getFirstName() + " " + user.getLastName(), user.getProfilePictureUrl());
            avatar.addClassNames("me-xs");
            avatar.setColorIndex(2);

            ContextMenu userMenu = new ContextMenu(avatar);
            userMenu.setOpenOnClick(true);
            userMenu.addItem("Iziet", e -> {
                authenticatedUser.logout();
            });

            Span name = new Span(user.getFirstName() + " " + user.getLastName());
            name.addClassNames("font-medium", "text-s", "text-secondary");

            layout.add(avatar, name);
        } else {
            Anchor loginLink = new Anchor("login", "Pieslēgties");
            Anchor regLink = new Anchor("registration", "Reģistrēties");
            layout.add(loginLink, regLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
