package ro.quickorder.backend.sendEmail;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.service.ReservationService;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author R. Lupoaie
 */
@Service
public class JavaMailUtil {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    private static String myAccountEmail = "";
    private static String password = "";
    private static String reciever = "";

    public void sendMail(String recepient, String mailText, String mailTitle) {
        Properties properties = new Properties();
        System.out.println("Preapering to send email");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        if (myAccountEmail.equals("")) {
            readEmailFromFile();
        }
        if(recepient.equals("")){
            recepient = reciever;
        }
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, recepient, mailText, mailTitle);
        try {
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException ex) {
            LOG.error("Error at sending the mail!");
            throw new BadRequestException("Error at sending the mail!");
        }
    }

    private  Message prepareMessage(Session session, String myAccountEmail,
                                          String recepient, String mailText, String mailTitle) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
            message.setSubject(mailTitle);

            String msg = mailText;
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
        } catch (MessagingException ex) {
            LOG.error("Error at setting the message for the mail!");
            throw new BadRequestException("Error at setting the message for the mail!");
        }
        return message;
    }

    private void readEmailFromFile() {
        File file = new File(
                getClass().getClassLoader().getResource("mail.txt").getFile()
        );
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            myAccountEmail = reader.readLine();
            password = reader.readLine();
            reciever = reader.readLine();
            System.out.println(myAccountEmail);
            System.out.println(password);
            System.out.println(reciever);

            reader.close();
        } catch (IOException ex) {
            LOG.error("Error at reading the data from mail folder!");
            throw new BadRequestException("Error at reading the data from mail folder!");
        }
    }

}
