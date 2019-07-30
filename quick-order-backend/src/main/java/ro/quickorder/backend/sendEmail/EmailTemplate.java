package ro.quickorder.backend.sendEmail;

import java.sql.Timestamp;

/**
 * @author R. Lupoaie
 */
public class EmailTemplate {

    public static String createTextReservation(int numberOfPersons, Timestamp checkInTime, Timestamp checkOutTime, boolean confirmed) {
        StringBuilder mailText = new StringBuilder("The reservation you made for ");
        mailText.append(numberOfPersons + "");
        mailText.append(" persons, starting on ").append(checkInTime + "").append(" to ").append(checkOutTime + "");
        if (confirmed) {
            mailText.append(" has been confirmed.");
        } else {
            mailText.append(" has been made.");
        }
        return mailText.toString();
    }

    public static String createTitleReservation() {
        return "Reservation made";
    }


}
