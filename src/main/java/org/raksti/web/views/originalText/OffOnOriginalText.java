package org.raksti.web.views.originalText;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.Pages;
import org.raksti.web.data.service.PagesService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@PageTitle("Ieslēgt/izslēgt lapas")
@Route(value = "pages-edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OffOnOriginalText extends VerticalLayout {

    private final PagesService pagesService;

    public OffOnOriginalText(PagesService pagesService) {
        this.pagesService = pagesService;

        Checkbox checkbox = new Checkbox();
        checkbox.setLabel("Izslēgt/ieslēgt oriģināl tekstu");

        if (pagesService.findById("original-text").isEmpty()) {
            pagesService.createOriginalText();
            checkbox.setValue(false);
        } else {
            Optional<Pages> maybePages = pagesService.findById("original-text");
            if (maybePages.isPresent()) {
                Pages pages = maybePages.get();
                checkbox.setValue(pages.isPowerOn());
            }
        }





        checkbox.addValueChangeListener(e -> {
            Optional<Pages> maybePages = pagesService.findById("original-text");
            if (maybePages.isPresent()) {
                Pages pages = maybePages.get();
                pages.setPowerOn(e.getValue());
                pagesService.savePage(pages);
            }
        });

        add(checkbox);
    }
}
