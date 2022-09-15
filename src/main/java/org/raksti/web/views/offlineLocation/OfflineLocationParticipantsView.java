package org.raksti.web.views.offlineLocation;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.entity.OfflineLocation;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.OfflineLocationService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Offline Exams Participants")
@Route(value = "list-of-offline-participants", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class OfflineLocationParticipantsView extends VerticalLayout {

    public OfflineLocationParticipantsView(OfflineLocationService offlineLocationService) {

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

        grid.addColumn(map -> map.get("city")).setHeader("city");
        grid.addColumn(map -> map.get("address")).setHeader("address");
        grid.addColumn(map -> map.get("participant")).setHeader("participant");
        grid.getColumns().forEach(gridColumn -> gridColumn.setAutoWidth(true));

        add(grid);
    }
}
