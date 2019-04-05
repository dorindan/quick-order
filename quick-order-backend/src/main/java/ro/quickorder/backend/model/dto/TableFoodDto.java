package ro.quickorder.backend.model.dto;

/**
 *  Data transfer object for {@link ro.quickorder.backend.model.TableFood}
 *
 *  *@author R. Lupoaie
 */
public class TableFoodDto {

    private int tableNr;
    private int seats;
    private boolean windowView;
    private int floor;

    public TableFoodDto() {

    }

    public TableFoodDto(int tableNr, int seats, boolean windowView, int floor) {
        this.tableNr = tableNr;
        this.seats = seats;
        this.windowView = windowView;
        this.floor = floor;
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

}
