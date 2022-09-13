package com.example.application.views.offlineExam;

import com.example.application.data.entity.OfflineExam;
import com.example.application.data.entity.User;
import com.example.application.data.service.OfflineExamService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Offline Exams Participants")
@Route(value = "list-of-offline-exams-participants", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class ParticipantsView extends VerticalLayout {

    public ParticipantsView(OfflineExamService offlineExamService) {

        List<Map<String, String>> participants = new ArrayList<>();
        Map<String, String> item;
        for (OfflineExam offlineExam : offlineExamService.getAll()) {
            for (User user : offlineExam.getParticipants()) {
                item = new HashMap<>();
                item.put("city", offlineExam.getCity());
                item.put("address", offlineExam.getAddress());
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
