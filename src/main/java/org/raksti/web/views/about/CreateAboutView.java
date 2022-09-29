package org.raksti.web.views.about;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.About;
import org.raksti.web.data.service.AboutService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@PageTitle("Rediģēt Sākumlapu")
@Route(value = "create-aboutview", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateAboutView extends VerticalLayout {

    private final Button save = new Button("Saglabāt");
    private final TextField title = new TextField("Virsraksts");
    private final TextArea textArea = new TextArea("Saturs");

    private final AboutService aboutService;

    public CreateAboutView(AboutService aboutService) {
        this.aboutService = aboutService;
        loadContent();
        landing();
    }

    private void landing() {
        //title
        title.setMinWidth("400px");
        title.setClearButtonVisible(true);
        //content
        textArea.setMinWidth("400px");
        textArea.setClearButtonVisible(true);
        //save Button
        save.addClickListener(e -> saveContent());

        add(title, textArea, save);
    }

    private void loadContent() {
        List<About> aboutList = aboutService.getAll();
        if (aboutList.size() > 0) {
            About about = aboutList.get(0);
            title.setValue(about.getTitle());
            textArea.setValue(about.getText());
        }
    }


    private void saveContent() {
        aboutService.deleteAll();
        aboutService.saveAbout(new About(title.getValue(), textArea.getValue()));
        loadContent();
        Notification.show("Saglabāts").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}
