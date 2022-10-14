package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Klātienes diktāta dalībnieki")
@Route(value = "list-of-offline-participants", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OfflineLocationParticipantsView extends VerticalLayout {


    private final Button export = new Button("Export");

    private final static Logger logger = LoggerFactory.getLogger(OfflineLocationParticipantsView.class);

    public OfflineLocationParticipantsView(OfflineLocationService offlineLocationService) {
        getStyle().set("padding-top", "30px");

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

        grid.addColumn(map -> map.get("city")).setHeader("Pilsēta");
        grid.addColumn(map -> map.get("address")).setHeader("Adrese");
        grid.addColumn(map -> map.get("participant")).setHeader("Dalībnieks");
        grid.getColumns().forEach(gridColumn -> gridColumn.setAutoWidth(true));

        Anchor link = new Anchor(writeToCSV(offlineLocationService.getAll()));
        link.add("Download");
        link.getElement().setAttribute("download", true);

        add(grid, export, link);

    }

    private static String writeToCSV(List<OfflineLocation> offlineLocations)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("list.csv"), StandardCharsets.UTF_16));
            for (OfflineLocation location : offlineLocations)
            {
                for (User user: location.getParticipants()) {
                    String oneLine =
                            user.getFirstName() + "," +
                            user.getLastName() + "," +
                            user.getEmail() + "," +
                            user.getAge() + "," +
                            user.getEducation() + "," +
                            user.getCity() + "," +
                            user.getCountry()  + "," +
                            user.getGender() + "," +
                            user.getTelNumber() + "," +
                            user.getLanguage() + "," +
                            user.getBirthday() + "," +
                            location.getAddress() + "," +
                            location.getCity();
                    bw.write(oneLine);
                    bw.flush();
                    bw.newLine();
                }

            }
            bw.close();
            return "list.csv";
        } catch (IOException e){
            logger.error(e.getMessage(), e);
            return "Mistake";
        }
    }



    private InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stream;
    }

}
