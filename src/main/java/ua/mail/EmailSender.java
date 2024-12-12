package ua.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailSender {

    public static void main(String[] args) {
        // Configuration
        String smtpHost = "smtp.gmail.com";
        String smtpPort = "587";
        String username = "sane4kayurchenko1212@gmail.com";
        String password = "lkyz pold hltv pkvo";
        String fromEmail = "sane4kayurchenko1212@gmail.com";

        // Recipient list
        List<String> recipients = new ArrayList<>();
        recipients.add("yurchenko.20031208@gmail.com");
        recipients.add("sane4kayurchenko1212@gmail.com");

        // Email content
        String subject = "Test HTML Email with Attachments";
        String htmlContent = "<h1>Hello!</h1><p>This is an <b>HTML</b> email with attachments.</p>";

        // Attachments
        List<String> attachments = new ArrayList<>();
        attachments.add("D:\\chmnu\\Network Programming\\net-java-mail\\src\\main\\java\\ua\\mail\\Mini-brain.pdf");
        attachments.add("D:\\chmnu\\Network Programming\\net-java-mail\\src\\main\\java\\ua\\mail\\Software control UAV.pdf");

        // Send email
        sendEmail(smtpHost, smtpPort, username, password, fromEmail, recipients, subject, htmlContent, attachments);
    }

    public static void sendEmail(String smtpHost, String smtpPort, String username, String password, String fromEmail,
                                 List<String> recipients, String subject, String htmlContent, List<String> attachments) {
        // Configure properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Create a session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }
            message.setSubject(subject);

            // Create multipart content
            Multipart multipart = new MimeMultipart();

            // Add HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(htmlPart);

            // Add attachments
            for (String filePath : attachments) {
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.attachFile(new File(filePath));
                multipart.addBodyPart(attachmentPart);
            }

            // Set the multipart content as the message content
            message.setContent(multipart);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipients);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email.");
        }
    }
}
