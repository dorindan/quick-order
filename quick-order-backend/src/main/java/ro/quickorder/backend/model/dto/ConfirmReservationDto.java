package ro.quickorder.backend.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.service.CustomDateDeserializer;
import ro.quickorder.backend.service.CustomDateSerializer;

import java.sql.Timestamp;
import java.util.List;

public class ConfirmReservationDto {
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Timestamp checkInTime;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Timestamp checkOutTime;
    private String status;
    private boolean confirmed;
    private Integer numberOfPersons;
    private String reservationName;
    private List<TableFoodDto> tableFoodListDto;

    public ConfirmReservationDto() {
    }

    public ConfirmReservationDto(Timestamp checkInTime, Timestamp checkOutTime, String status, boolean confirmed, Integer numberOfPersons, String reservationName, List<TableFoodDto> tableFoodListDto) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
        this.confirmed = confirmed;
        this.numberOfPersons = numberOfPersons;
        this.reservationName = reservationName;
        this.tableFoodListDto = tableFoodListDto;
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

    public boolean isConfirmed() {
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

    public List<TableFoodDto> getTableFoodListDto() {
        return tableFoodListDto;
    }

    public void setTableFoodListDto(List<TableFoodDto> tableFoodListDto) {
        this.tableFoodListDto = tableFoodListDto;
    }
}
