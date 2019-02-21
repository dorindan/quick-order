package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.repository.ReservationRepository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public void addReservation(ReservationDto reservation){

        reservation.setStatus("not accepted");
        reservation.setConfirmed(false);
        Timestamp checkOutTime = new Timestamp(reservation.getCheckInTime().getTime()+7200000);
        reservation.setCheckOutTime(checkOutTime);
        Reservation convertedReservation = ReservationConverter.toReservation(reservation);

        reservationRepository.save(convertedReservation);
    }

}
