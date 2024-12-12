package ua.mail;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class EmailReceiver {

    public static void main(String[] args) {
        String host = "imap.gmail.com";
        String username = "sane4kayurchenko1212@gmail.com";
        String password = "lkyz pold hltv pkvo";

        // Start periodic email checking
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkInbox(host, username, password);
            }
        }, 0, 60000); // Check every 60 seconds
    }

    public static void checkInbox(String host, String username, String password) {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", host);
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getInstance(props, null);
            Store store = session.getStore();
            store.connect(host, username, password);


            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);


            Message[] messages = inbox.getMessages();
            System.out.println("Total messages: " + messages.length);

            for (Message message : messages) {
                if (message instanceof MimeMessage) {
                    MimeMessage mimeMessage = (MimeMessage) message;

                    System.out.println("From: " + mimeMessage.getFrom()[0]);
                    System.out.println("Subject: " + mimeMessage.getSubject());
                }
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error checking inbox.");
        }
    }
}

