package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.TableFood;

public class TableFoodDto {

    public int tableNr;
    public int seats;
    public boolean windowView;
    public int floor;
    public boolean free;

    public TableFoodDto(){

    }
    public TableFoodDto(TableFood tableFood){
        this.tableNr = tableFood.getTableNr();
        this.seats = tableFood.getSeats();
        this.windowView = tableFood.isWindowView();
        this.floor =tableFood.getFloor();
        this.free = tableFood.isFree();
    }

}
