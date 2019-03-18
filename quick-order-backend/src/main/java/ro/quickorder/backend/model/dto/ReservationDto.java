package ro.quickorder.backend.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ro.quickorder.backend.service.CustomDateDeserializer;
import ro.quickorder.backend.model.Reservation;

import java.sql.Timestamp;

public class ReservationDto {
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Timestamp checkInTime;
    private Timestamp checkOutTime;
    private String status;
    private boolean confirmed;
    private Integer numberOfPersons;
    private String reservationName;

    public ReservationDto() {
    }

    public ReservationDto(Reservation reservation){
        this.checkInTime = reservation.getCheckInTime();
        this.checkOutTime = reservation.getCheckOutTime();
        this.confirmed = reservation.isConfirmed();
        this.status = reservation.getStatus();
        this.numberOfPersons = reservation.getNumberOfPersons();
        this.reservationName = reservation.getReservationName();
    }

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }
}
