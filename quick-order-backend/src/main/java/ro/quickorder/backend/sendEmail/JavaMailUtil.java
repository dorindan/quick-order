package ro.quickorder.backend.sendEmail;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author R. Lupoaie
 */
public class JavaMailUtil {

    private static String myAccountEmail = "";
    private static String password = "";
    private static String reciever = "";

    public static void sendMail(String recepient, String mailText, String mailTitle) {
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
            System.out.println("Message sent succesfully");
        } catch (MessagingException ex) {
            System.out.println("Eroare la trimiterea mesajului");
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail,
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
            System.out.println("Eroare la setarea mesajului");
            Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    private static void readEmailFromFile() {
        String file = "src/main/java/ro/quickorder/backend/sendEmail/mail.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            myAccountEmail = reader.readLine();
            password = reader.readLine();
            reciever = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            System.out.println("Eroare la citirea datelor din fisier." + ex.getMessage());
        }
    }

}
