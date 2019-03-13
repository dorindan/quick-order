package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Feedback;
import ro.quickorder.backend.model.dto.FeedbackDto;

@Component
public class FeedbackConverter {

    public Feedback convertFeedbackDtoToFeedback(FeedbackDto feedbackDto){
        Feedback feedback= new Feedback();
        feedback.setRating(feedbackDto.getRating());
        feedback.setMessage(feedbackDto.getMessage());
        return feedback;
    }

    public FeedbackDto convertFeedbackToFeedbackDto(Feedback feedback){
        FeedbackDto feedbackDto= new FeedbackDto();
        feedbackDto.setRating(feedback.getRating());
        feedbackDto.setMessage(feedback.getMessage());
        return feedbackDto;
    }

}
