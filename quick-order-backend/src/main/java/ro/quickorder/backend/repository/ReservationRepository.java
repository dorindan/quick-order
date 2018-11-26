package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
