package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Feedback;
import ro.quickorder.backend.model.dto.FeedbackDto;

/**
 * Converts Feedbacks to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */
@Component
public class FeedbackConverter {
    public Feedback toFeedback(FeedbackDto feedbackDto) {
        if (feedbackDto == null) {
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setRating(feedbackDto.getRating());
        feedback.setMessage(feedbackDto.getMessage());
        return feedback;
    }

    public FeedbackDto toFeedbackDto(Feedback feedback) {
        if (feedback == null) {
            return null;
        }
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setRating(feedback.getRating());
        feedbackDto.setMessage(feedback.getMessage());
        return feedbackDto;
    }
}
