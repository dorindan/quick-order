package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.repository.FeedbackRepository;

@RestController
public class FeedbackResource {
    @Autowired
    FeedbackRepository feedbackRepository;

    @RequestMapping(path = "/feedback", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return feedbackRepository.findById(id).toString();
    }
}
