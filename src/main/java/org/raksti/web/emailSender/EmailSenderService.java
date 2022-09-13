package org.raksti.web.emailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;

@Service
public class EmailSenderService {


    private final JavaMailSender mailSender;
    private final String SENDER = "torino1337@gmail.com";

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String body, String subject) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(SENDER);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail sent...");

    }

    public void sendEmailWithAttachment(String toEmail, String body, String subject, String attachment) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(SENDER);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setText(body, true);
            mimeMessage.setSubject(subject);


            if (!attachment.equals("")) {
                FileSystemResource file = new FileSystemResource(new File(attachment));
                mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
            }

            mailSender.send(mimeMessage);
            System.out.println("Successfully sent");

        } catch (MessagingException e) {
            System.out.println("Error ocured");
        }
    }


}
