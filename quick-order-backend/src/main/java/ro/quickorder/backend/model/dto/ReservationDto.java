package ro.quickorder.backend.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import ro.quickorder.backend.service.CustomDateDeserializer;
import ro.quickorder.backend.model.Reservation;

import java.sql.Timestamp;

/**
 *  Data transfer object for {@link Reservation}
 *
 *  *@author R. Lupoaie
 */
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

    public ReservationDto(Timestamp checkInTime, Timestamp checkOutTime, String status, boolean confirmed, Integer numberOfPersons, String reservationName) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.confirmed = confirmed;
        this.status = status;
        this.numberOfPersons = numberOfPersons;
        this.reservationName = reservationName;
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

    public Boolean isConfirmed() {
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

    public static final class Builder {
        private Timestamp checkInTime;
        private Timestamp checkOutTime;
        private Integer numberOfPersons;
        private boolean confirmed;
        private String status;
        private String reservationName;


        public ReservationDto.Builder withCheckInTime(Timestamp checkInTime) {
            this.checkInTime = checkInTime;
            return this;
        }

        public ReservationDto.Builder withnumberOfPersons(Integer numberOfPersons) {
            this.numberOfPersons = numberOfPersons;
            return this;
        }
        public ReservationDto build() {
            return new ReservationDto(checkInTime, checkOutTime, status, confirmed, numberOfPersons, reservationName);
        }
    }
}
