package org.raksti.web.views.sendEmailsPage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.raksti.web.data.service.UserRepository;
import org.raksti.web.emailSender.EmailSenderService;
import org.raksti.web.views.MainLayout;
import org.raksti.web.views.registration.EmailAndPasswordValidation;

import javax.annotation.security.RolesAllowed;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String, String> emails = new HashMap<>();


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
            String line = sc.nextLine();
            String [] arr = line.split(",");
            emails.put(arr[0], arr[1]);
            System.out.println(arr[0] + "/" + arr[1]);
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
        for(Map.Entry<String, String> email: emails.entrySet()) {
            emailSenderService.sendEmail(email.getKey(), modifyText(rte.getValue(), email.getValue()), subject.getValue());
            System.out.println(email);
        }
        this.emails.clear();
        upload.clearFileList();
    }

    private String modifyText(String text, String id) {
        text = text.replace("[id]", id);

        return text;
    }



}
