package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.Feedback;

public class FeedbackDto {

    private int rating;
    private String message;


    public FeedbackDto(){

    }

    public FeedbackDto(int rating, String message){
        this.rating = rating;
        this.message = message;
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
