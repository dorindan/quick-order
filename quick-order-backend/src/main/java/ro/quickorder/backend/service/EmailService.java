package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.sendEmail.EmailTemplate;
import ro.quickorder.backend.sendEmail.JavaMailUtil;

import java.sql.Timestamp;

/**
 * @author R. Lupoaie
 */
@Service
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    private JavaMailUtil javaMailUtil;

    public void sendReservationMail(int numberOfPersons, Timestamp checkInTime, Timestamp checkOutTime, User user, boolean confirmed) {
        String mailTitle = EmailTemplate.createTitleReservation();
        String mailText = EmailTemplate.createTextReservation(numberOfPersons, checkInTime, checkOutTime, confirmed);

        String mailReceiver = "";

        if (user != null && user.getEmail() != null) {
            mailReceiver = user.getEmail();
        }
        javaMailUtil.sendMail(mailReceiver, mailText, mailTitle);
    }

}
