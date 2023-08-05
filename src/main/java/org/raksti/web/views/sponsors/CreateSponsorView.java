package org.raksti.web.views.sponsors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.Sponsor;
import org.raksti.web.data.service.SponsorService;
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@PageTitle("Par inciatīvu - rediģēt")
@Route(value = "create-sponsors", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateSponsorView extends VerticalLayout {

    private final static Logger logger = LoggerFactory.getLogger(CreateSponsorView.class);

    private final TextField link = new TextField("Saite uz sponsoru");
    private final TextField name = new TextField("Nosaukums");
    private final MultiFileMemoryBuffer mb = new MultiFileMemoryBuffer();
    private final Upload singleFileUpload = new Upload(mb);
    private final Select<String> type = new Select<>();
    private final Button saveButt = new Button("Pievienot");
    private final Button delete = new Button("Dzēst");
    private final Grid<Sponsor> grid = new Grid<>(Sponsor.class, false);
    private List<Sponsor> sponsorList;
    private byte[] data;

    private final SponsorService sponsorService;

    public CreateSponsorView(SponsorService sponsorService) throws IOException {
        this.sponsorService = sponsorService;
        loadSponsors();
        landing();
    }

    private void landing() {
        getStyle().set("padding-top", "30px");
        //link with src on sponsor
        link.setClearButtonVisible(true);
        link.setMinWidth("400px");
        link.setPlaceholder("Saite");
        //Upload system
        singleFileUpload.setMaxFiles(1);
        singleFileUpload.setWidth("400px");
        singleFileUpload.setAcceptedFileTypes("image/jpeg","image/jpg", "image/png", "image/gif", "image/svg");
        singleFileUpload.addSucceededListener(event -> {
            String attachmentName = event.getFileName();
            try {
                // The image can be jpg png or gif, but we store it always as png file in this example
                BufferedImage inputImage = ImageIO.read(mb.getInputStream(attachmentName));
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                this.data = pngContent.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //sponsor type
        type.setLabel("Izvelēties starp atbalstītāju un norises vieti");
        type.setItems("Atbalstītājs", "Norises vieta");
        type.setEmptySelectionAllowed(true);
        // save button
        saveButt.setWidth("400px");
        saveButt.addClickListener(e -> saveContent());
        //grid
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Sponsor::getName).setHeader("Nosaukums").setAutoWidth(true);
        grid.addColumn(Sponsor::getType).setHeader("Tips").setAutoWidth(true);
        grid.addColumn(Sponsor::getLink).setHeader("Saite").setAutoWidth(true);
        grid.setItems(sponsorList);
        //delete Button
        delete.setWidth("400ox");
        delete.addClickListener(e -> deleteItems());
        //name
        name.setMinWidth("400px");
        name.setClearButtonVisible(true);
        name.setPlaceholder("Nosaukums");
        //
        add(link, name, singleFileUpload, type, saveButt, grid, delete);
    }

    private void deleteItems() {
        Set<Sponsor> deleteList = grid.getSelectedItems();
        if (!deleteList.isEmpty()) {
            sponsorService.deleteEntities(deleteList);
            loadSponsors();
            grid.setItems(sponsorList);
            Notification.show("Sponsors dzēsts!", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else Notification.show("Izvēlēties, ko dzēst", 5000, Notification.Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
    }

    private void loadSponsors() {
        sponsorList = sponsorService.getAllEntities();
    }

    private boolean validate() {
        return !link.getValue().isEmpty() && !name.getValue().isEmpty() && !type.getValue().isEmpty();
    }

    private void saveContent() {
        if (validate()) {
            sponsorService.save(new Sponsor(link.getValue(), data, name.getValue(), type.getValue()));
            loadSponsors();
            grid.setItems(sponsorList);
            this.data = null;
            singleFileUpload.clearFileList();
        }
    }


}
