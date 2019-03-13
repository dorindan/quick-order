package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Feedback;
import ro.quickorder.backend.model.dto.FeedbackDto;

import static org.junit.Assert.*;

/**
 * Unit test for {@link FeedbackConverter}
 *
 * @author R. Lupoaie
 */
public class FeedbackConverterTest {

    private FeedbackConverter feedbackConverter = new FeedbackConverter();

    @Test
    public void testConvertFeedbackToDto(){
        Feedback feedback = new Feedback(4,"Message",null);

        FeedbackDto feedbackDto = feedbackConverter.toFeedbackDto(feedback);

        assertEquals(feedbackDto.getMessage(), feedback.getMessage());
        assertEquals(feedbackDto.getRating(), feedback.getRating());
    }
    @Test
    public void testConvertFeedbackToDtoWhenFeedbackIsNull(){
        FeedbackDto feedbackDto = feedbackConverter.toFeedbackDto(null);

        assertNull(feedbackDto);
    }

    @Test
    public void testConvertDtoToFeedback(){
        FeedbackDto feedbackDto = new FeedbackDto(4,"Message");

        Feedback feedback = feedbackConverter.toFeedback(feedbackDto);

        assertEquals(feedbackDto.getMessage(), feedback.getMessage());
        assertEquals(feedbackDto.getRating(), feedback.getRating());
        assertNull(feedback.getMenuItem());
    }
    @Test
    public void testConvertDtoToFeedbackWhenDtoIsNull(){
        Feedback feedback = feedbackConverter.toFeedback(null);

        assertNull(feedback);
    }
}