package org.raksti.web.views.originalText;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.Pages;
import org.raksti.web.data.service.PagesService;
import org.raksti.web.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Ieslēgt/izslēgt lapas")
@Route(value = "pages-edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OffOnOriginalText extends VerticalLayout {
    private final PagesService pagesService;

    public final static String ORIGINAL_TEXT_PAGE = "original-text";
    public final static String OFFLINE_PAGE = "offline-page";

    @Autowired
    public OffOnOriginalText(@NotNull PagesService pagesService) {
        this.pagesService = pagesService;

        add(getCheckBox("Izslēgt/ieslēgt diktāta oriģināltekstu", ORIGINAL_TEXT_PAGE));
        add(getCheckBox("Izslēgt/ieslēgt klātienes reģistrācijas lapu", OFFLINE_PAGE));
    }

    private Checkbox getCheckBox(@NotNull String comment, @NotNull String id) {
        Checkbox checkbox = new Checkbox();
        checkbox.setLabel(comment);
        Pages pages;

        Optional<Pages> maybePages = pagesService.findById(id);
        if (maybePages.isPresent()) {
            pages = maybePages.get();
            checkbox.setValue(pages.isPowerOn());
        } else {
            pages = new Pages(id, false);
        }
        checkbox.setValue(pages.isPowerOn());

        checkbox.addValueChangeListener(e -> {
            pages.setPowerOn(e.getValue());
            pagesService.savePage(pages);
        });

        return checkbox;
    }
}
