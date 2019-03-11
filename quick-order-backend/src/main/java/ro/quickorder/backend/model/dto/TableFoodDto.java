package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.TableFood;

public class TableFoodDto {

    private int tableNr;
    private int seats;
    private boolean windowView;
    private int floor;
    private boolean free;

    public TableFoodDto(){

    }
    public TableFoodDto(TableFood tableFood){
        this.tableNr = tableFood.getTableNr();
        this.seats = tableFood.getSeats();
        this.windowView = tableFood.isWindowView();
        this.floor =tableFood.getFloor();
        this.free = tableFood.isFree();
    }

    public int getTableNr() {
        return tableNr;
    }

    public void setTableNr(int tableNr) {
        this.tableNr = tableNr;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isWindowView() {
        return windowView;
    }

    public void setWindowView(boolean windowView) {
        this.windowView = windowView;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
