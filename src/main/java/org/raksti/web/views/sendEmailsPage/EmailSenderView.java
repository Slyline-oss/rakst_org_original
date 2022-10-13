package org.raksti.web.views.sendEmailsPage;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.receivers.FileData;
import  org.raksti.web.data.entity.User;
import org.raksti.web.data.service.UserRepository;
import  org.raksti.web.emailSender.EmailSenderService;
import  org.raksti.web.views.MainLayout;
import  org.raksti.web.views.registration.EmailAndPasswordValidation;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@PageTitle("Sūtīt vēstules")
@Route(value = "email-sender", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EmailSenderView extends VerticalLayout {

    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final EmailAndPasswordValidation emailAndPasswordValidation;

    private final TextArea rte = new TextArea();
    private final Button send = new Button("Sūtīt e-pastu");
    private final TextField subject = new TextField("Virsraksts");
    private final MultiFileMemoryBuffer mfmb = new MultiFileMemoryBuffer();
    private final Upload upload = new Upload(mfmb);
    private String fileName = "";
    private List<String> emails = new ArrayList<>();


    public EmailSenderView(EmailSenderService emailSenderService, UserRepository userRepository, EmailAndPasswordValidation emailAndPasswordValidation) {

        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;
        this.emailAndPasswordValidation = emailAndPasswordValidation;


        makeLayout();

        upload.setAcceptedFileTypes(".csv");
        upload.addSucceededListener(e -> {
            displayList(mfmb.getInputStream(e.getFileName()));
        });
        send.addClickListener(e -> {
           if (fileName != null) {
               sendEmail();
           }
        });

    }

    private void displayList(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        while (sc.hasNext()) {
            emails.add(sc.next());
        }
    }

    private void makeLayout() {
        getStyle().set("padding-top", "30px");
        UploadI18N latvianUploadi18n = new UploadI18N();
        upload.setI18n(latvianUploadi18n);


        subject.setPlaceholder("Vēstules virsraksts");
        subject.setClearButtonVisible(true);
        subject.isRequired();

        rte.setMinWidth("400px");
        rte.setMinHeight("300px");

        send.setIcon(new Icon(VaadinIcon.CHECK_CIRCLE));

        add(subject, rte, upload, send);

    }

    private void sendEmail() {
        for(String email: emails) {
            emailSenderService.sendEmail(email, rte.getValue(), subject.getValue());
        }
    }



}
