package org.raksti.web.views.sponsors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.SucceededEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.raksti.web.data.entity.Sponsor;
import org.raksti.web.data.service.SponsorService;
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.xml.transform.stream.StreamSource;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.List;

@PageTitle("Par inciatīvu - rediģēt")
@Route(value = "create-sponsors", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateSponsorView extends VerticalLayout {

    private final static Logger logger = LoggerFactory.getLogger(CreateSponsorView.class);

    private final TextField link = new TextField("Saite uz sponsoru");
    private final TextField name = new TextField("Nosaukums");
    private final MemoryBuffer mb = new MemoryBuffer();
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
        showImage();
    }

    private void landing() {
        //link with src on sponsor
        link.setClearButtonVisible(true);
        link.setMinWidth("400px");
        link.setPlaceholder("Saite");
        //Upload system
        singleFileUpload.setMaxFiles(1);
        singleFileUpload.setWidth("400px");
        singleFileUpload.addSucceededListener(e -> {
            try {
                logger.info(e.getFileName());
                handleImage(e);
            } catch (IOException ex) {
                ex.printStackTrace();
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
    }

    private void loadSponsors() {
        sponsorList = sponsorService.getAllEntities();
    }

    private boolean validate() {
        return this.data != null && !link.getValue().isEmpty() && !name.getValue().isEmpty();
    }

    private void saveContent() {
        if (validate()) {
            sponsorService.save(new Sponsor(link.getValue(), data, name.getValue()));
            loadSponsors();
            grid.setItems(sponsorList);
            this.data = null;
            singleFileUpload.clearFileList();
        }
    }

    private void handleImage(SucceededEvent event) throws IOException {
        InputStream inputStream = mb.getInputStream();
        this.data = inputStream.readAllBytes();
        inputStream.close();
    }

    private void showImage() throws IOException {

    }


}
