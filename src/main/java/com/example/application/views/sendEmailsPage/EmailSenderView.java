package com.example.application.views.sendEmailsPage;

import com.example.application.data.entity.User;
import com.example.application.data.service.UserRepository;
import com.example.application.emailSender.EmailSenderService;
import com.example.application.security.UserDetailsServiceImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Send Emails Page")
@Route(value = "email-sender", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class EmailSenderView extends VerticalLayout {

    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;

    private final RichTextEditor rte = new RichTextEditor();
    private final Button send = new Button("Sūtīt e-mailu");
    private final TextField subject = new TextField("Virsraksts");
    private final MultiFileMemoryBuffer mfmb = new MultiFileMemoryBuffer();
    private final Upload upload = new Upload(mfmb);
    private String fileName;
    private List<User> listOfUses;

    public EmailSenderView(EmailSenderService emailSenderService, UserRepository userRepository) {

        this.emailSenderService = emailSenderService;
        this.userRepository = userRepository;


        makeLayout();
        listOfUses = userRepository.findAll();

        upload.addSucceededListener(e -> fileName = e.getFileName());
        send.addClickListener(e -> {
           if (fileName != null) {
               sendEmail();
           }
        });

    }

    private void makeLayout() {
        UploadI18N latvianUploadi18n = new UploadI18N();
        upload.setI18n(latvianUploadi18n);


        subject.setPlaceholder("Vēstules virsraksts");
        subject.setClearButtonVisible(true);
        subject.isRequired();

        rte.setMaxHeight("500px");

        send.setIcon(new Icon(VaadinIcon.CHECK_CIRCLE));

        add(subject, rte, upload, send);

    }

    private void sendEmail() {
        for (int i = 0; i < listOfUses.size(); i++) {
            emailSenderService.sendEmailWithAttachment(listOfUses.get(i).getEmail(), rte.getHtmlValue(), subject.getValue(), fileName);
        }
    }



}
