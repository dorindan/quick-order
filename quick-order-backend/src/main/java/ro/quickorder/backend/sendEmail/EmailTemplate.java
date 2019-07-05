package ro.quickorder.backend.sendEmail;

import ro.quickorder.backend.model.User;

import java.sql.Timestamp;

/**
 * @author R. Lupoaie
 */
public class EmailTemplate {

    public static String createTextReservation(int numberOfPersons, Timestamp checkInTime, Timestamp checkOutTime, boolean confirmed){
        String mailText = "The reservation you made " + " for " + numberOfPersons
                + " persons, starting on " + checkInTime + " to " + checkOutTime ;
        if(confirmed){
            mailText += " has been confirmed.";
        } else {
            mailText += " has been made.";
        }
        return mailText;
    }

    public  static String createTitleReservation(){
        return "Reservation made";
    }



}
