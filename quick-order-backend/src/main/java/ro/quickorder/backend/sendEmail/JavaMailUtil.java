package ro.quickorder.backend.sendEmail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author R. Lupoaie
 */
public class JavaMailUtil {

    public static void sendMail(String recepient) {
        Properties properties = new Properties();
        System.out.println("Preapering to send email");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        String myAccountEmail = "robert_tu_1@yahoo.com";
        String password = "ganditorul_admin";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        System.out.println(session + " " + recepient);

        Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
            message.setSubject("My First Email From Java APP");
            //message.setText("Hey There, \n Look my email!");

            String msg = "Hey There, \n Look my email!";
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
        } catch (MessagingException ex) {
            System.out.println("Eroare la setarea mesajului");
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Transport.send(message);
            System.out.println("Message sent succesfully");
        } catch (MessagingException ex) {
            System.out.println("Eroare la trimiterea mesajului");
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
