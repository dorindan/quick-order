package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "Select r FROM Reservation r left join fetch r.tables WHERE r.reservationName = :name ")
    Reservation findByReservationNameWithTables(String name);

    Reservation findByReservationName(String name);

    @Query(value = "FROM Reservation r WHERE :tableFood member of r.tables ORDER BY r.checkInTime ASC ")
    List<Reservation> findReservationByTable(@Param("tableFood") TableFood tableFood);

    List<Reservation> findAll();

    @Query(value = "Select distinct r From Reservation r left join fetch r.tables")
    List<Reservation> findAllWithTables();

    @Query(value = "Select r.tables From Reservation r where r.checkInTime < :maxCheckOutTime and  r.checkOutTime > :minCheckInTime")
    List<TableFood> findTablesWithReservationsBetween(@Param("minCheckInTime") Timestamp minCheckInTime, @Param("maxCheckOutTime") Timestamp maxCheckOutTime);
}
