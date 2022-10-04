package org.raksti.web.views.donation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.About;
import org.raksti.web.data.entity.DonateText;
import org.raksti.web.data.service.DonateService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Atbalsts un saziņa - rediģēt")
@Route(value = "donations-edit", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateDonationView extends VerticalLayout {

    private final TextArea textArea = new TextArea();
    private final Button saveButt = new Button("Saglabāt");

    private final DonateService donateService;

    public CreateDonationView(DonateService donateService) {
        this.donateService = donateService;
        loadContent();
        landing();
    }

    private void landing() {
        textArea.setClearButtonVisible(true);
        textArea.setMinWidth("500px");
        textArea.setMinHeight("600px");

        saveButt.addClickListener(e -> saveContent());

        add(textArea, saveButt);
    }

    private void loadContent() {
        List<DonateText> donateTextList = donateService.getAll();
        if (donateTextList.size() > 0) {
            DonateText donate = donateTextList.get(0);
            textArea.setValue(donate.getText());
        }
    }

    private void saveContent() {
        donateService.deleteAll();
        donateService.saveDonate(new DonateText(textArea.getValue()));
        loadContent();
        Notification.show("Saglabāts", 5000, Notification.Position.TOP_START);
    }


}
