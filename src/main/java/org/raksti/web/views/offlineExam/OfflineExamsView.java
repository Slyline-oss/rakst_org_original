package org.raksti.web.views.offlineExam;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jsoup.internal.StringUtil;
import org.raksti.web.data.entity.OfflineExam;
import org.raksti.web.data.entity.User;
import org.raksti.web.data.service.OfflineExamService;
import org.raksti.web.security.AuthenticatedUser;
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Offline Exams")
@Route(value = "list-of-offline-exams", layout = MainLayout.class)
@RolesAllowed({"USER", "ADMIN"})
public class OfflineExamsView extends VerticalLayout {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final OfflineExamService offlineExamService;
    private final AuthenticatedUser authenticatedUser;
    private final Grid<OfflineExam> offlineExamGrid = new Grid<>(OfflineExam.class);

    public OfflineExamsView(OfflineExamService offlineExamService, AuthenticatedUser authenticatedUser) {
        this.offlineExamService = offlineExamService;
        this.authenticatedUser = authenticatedUser;

        getData();
        add(offlineExamGrid);
    }

    private void getData() {
        offlineExamGrid.removeAllColumns();
        offlineExamGrid.addColumn(OfflineExam::getCity).setHeader("City");
        offlineExamGrid.addColumn(OfflineExam::getAddress).setHeader("Address");
        offlineExamGrid.addColumn(OfflineExam::getTotalSlots).setHeader("Total Slots");
        offlineExamGrid.addColumn(OfflineExam::getAvailableSlots).setHeader("Available Slots");
        offlineExamGrid.addComponentColumn(offlineExam -> {
            List<String> emails = offlineExam.getParticipants().stream().map(User::getEmail).collect(Collectors.toList());
            return new Label(StringUtil.join(emails, ","));
        });
        offlineExamGrid.addComponentColumn(offlineExam -> {
            Button participate = new Button("Participate");
            participate.addClickListener(buttonClickEvent -> {
                //TODO: do some magic here to register participation :)
                // fix the travesty below :(
                // because right now this is broken ! ! !
//                logger.info("slots: " + offlineExamService.findById(offlineExam.getId()).getAvailableSlots());
                Integer availableSlots = offlineExam.getAvailableSlots();
                if (availableSlots < 1) {
                    participate.setEnabled(false);
                }
                User loggedInUser = authenticatedUser.get().get();
                if (offlineExam.getParticipants().contains(loggedInUser) || availableSlots < 1) {
                    Notification.show("No! NO cookie for you!").addThemeVariants(NotificationVariant.LUMO_ERROR);
                } else {
                    offlineExam.getParticipants().add(loggedInUser);
                    offlineExam.setAvailableSlots(availableSlots-1);
                    offlineExamService.save(offlineExam);
                }
                offlineExamGrid.getDataProvider().refreshAll();
            });
            return participate;
        });
        offlineExamGrid.getColumns().forEach(offlineExamColumn -> offlineExamColumn.setAutoWidth(true));
        offlineExamGrid.setItems(offlineExamService.getAll());
    }
}
