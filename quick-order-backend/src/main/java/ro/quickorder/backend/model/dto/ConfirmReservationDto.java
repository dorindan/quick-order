package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.TableFood;

import java.util.List;

public class ConfirmReservationDto {
    ReservationDto reservationDto;
    List<TableFoodDto> tableFoodListDto;

    public ConfirmReservationDto() {
    }

    public ConfirmReservationDto(ReservationDto reservationDto, List<TableFoodDto> tableFoodListDto) {
        this.reservationDto = reservationDto;
        this.tableFoodListDto = tableFoodListDto;
    }

    public List<TableFoodDto> getTableFoodListDto() {
        return tableFoodListDto;
    }

    public void setTableFoodListDto(List<TableFoodDto> tableFoodListDto) {
        this.tableFoodListDto = tableFoodListDto;
    }

    public ReservationDto getReservationDto() {
        return reservationDto;
    }

    public void setReservationDto(ReservationDto reservationDto) {
        this.reservationDto = reservationDto;
    }
}
