package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {
}
