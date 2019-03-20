package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByReservationName(String name);

    List<Reservation> findAll();

    @Query(value = "Select r.tables From Reservation r where r.checkInTime < :timestampOut and  r.checkOutTime > :timestampIn")
    List<TableFood> findReservationsByInside(Timestamp timestampIn, Timestamp timestampOut);
}
