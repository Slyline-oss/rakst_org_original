package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.OfflineLocationRepository;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Klātienes diktāta dalībnieki")
@Route(value = "list-of-offline-participants", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OfflineLocationParticipantsView extends VerticalLayout {
    private final UserRepository userRepository;
    private final OfflineLocationRepository offlineLocationRepository;

    @Autowired
    public OfflineLocationParticipantsView(@NotNull OfflineLocationService offlineLocationService,
                                           @NotNull UserRepository userRepository,
                                           @NotNull OfflineLocationRepository offlineLocationRepository) {
        this.userRepository = userRepository;
        this.offlineLocationRepository = offlineLocationRepository;

        getStyle().set("padding-top", "30px");

        Grid<Map<String, String>> grid = getMapGrid(offlineLocationService);

        grid.addColumn(map -> map.get("city")).setHeader("Pilsēta");
        grid.addColumn(map -> map.get("address")).setHeader("Adrese");
        grid.addColumn(map -> map.get("participant")).setHeader("Dalībnieks");
        grid.getColumns().forEach(gridColumn -> gridColumn.setAutoWidth(true));

        StreamResource streamResource = new StreamResource("offline_participants.csv", this::writeToCSV);
        Anchor link = new Anchor(streamResource, "DOWNLOAD OFFLINE PARTICIPANTS LIST");
        link.getElement().setAttribute("download", true);

        Button deleteAll = new Button("CLEAR ALL DATA");
        deleteAll.addClickListener(e -> deleteAll());

        add(grid, link, deleteAll);
    }

    private void deleteAll() {
        Dialog confirmDialog = new Dialog();
        confirmDialog.setHeaderTitle("Delete all offline participants data");
        confirmDialog.add(new Label("Are you sure you want to DELETE ALL offline participant data?"));

        Button cancel = new Button("Cancel");
        cancel.addClickListener(e -> confirmDialog.close());

        Button delete = new Button("DELETE");
        delete.addClickListener(e -> {
            userRepository.clearOfflineParticipationData();
            offlineLocationRepository.resetToInitial();
            confirmDialog.close();
            UI.getCurrent().getPage().reload();
        });

        HorizontalLayout hl = new HorizontalLayout(cancel, delete);
        confirmDialog.add(hl);

        confirmDialog.open();
    }

    @NotNull
    private static Grid<Map<String, String>> getMapGrid(OfflineLocationService offlineLocationService) {
        List<Map<String, String>> participants = new ArrayList<>();
        Map<String, String> item;
        for (OfflineLocation offlineLocation : offlineLocationService.getAll()) {
            for (User user : offlineLocation.getParticipants()) {
                item = new HashMap<>();
                item.put("city", offlineLocation.getCity());
                item.put("address", offlineLocation.getAddress());
                item.put("participant", user.getEmail());
                participants.add(item);
            }
        }

        Grid<Map<String, String>> grid = new Grid<>();
        grid.setItems(participants);
        return grid;
    }

    private InputStream writeToCSV() {
        List<OfflineLocation> offlineLocations = offlineLocationRepository.findAll();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (OfflineLocation location : offlineLocations) {
            for (User user : location.getParticipants()) {
                String oneLine =
                        user.getFirstName() + "," +
                                user.getLastName() + "," +
                                user.getEmail() + "," +
                                user.getOfflineNote() + "," +
                                user.getAge() + "," +
                                user.getEducation() + "," +
                                user.getCity() + "," +
                                user.getCountry() + "," +
                                user.getGender() + "," +
                                user.getTelNumber() + "," +
                                user.getLanguage() + "," +
                                user.getBirthday() + "," +
                                location.getAddress() + "," +
                                location.getCity();
                try {
                    outputStream.write(oneLine.getBytes(StandardCharsets.UTF_8));
                    outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

}
