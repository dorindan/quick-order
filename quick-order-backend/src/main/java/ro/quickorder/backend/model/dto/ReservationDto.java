package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.Reservation;

import java.sql.Timestamp;

public class ReservationDto {

    public Timestamp checkInTime;
    public Timestamp checkOutTime;
    public boolean confirmed;
    public String status;
    public String reservationName;

    public UserDto user;
    public CommandDto command;

    public ReservationDto(){

    }


    public ReservationDto(Reservation reservation){
        this.checkInTime = reservation.getCheckInTime();
        this.checkOutTime = reservation.getCheckOutTime();
        this.confirmed = reservation.isConfirmed();
        this.status = reservation.getStatus();
        this.reservationName = reservation.getReservationName();
    }
}
