package ro.quickorder.backend.model;



import org.hibernate.annotations.GenericGenerator;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class TableFood {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    private int tableNr;
    private int seats;
    private boolean windowView;
    private int floor;
    private boolean active = true;

    @OneToMany(mappedBy = "table")
    private List<Command> commands;

    @ManyToMany(mappedBy = "tables")
    private List<Reservation> reservations;

    public TableFood(int tableNr, int seats, boolean windowView, int floor) {
        this.tableNr = tableNr;
        this.seats = seats;
        this.windowView = windowView;
        this.floor = floor;
    }

    public TableFood() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableFood tableFood = (TableFood) o;
        return tableNr == tableFood.tableNr &&
                seats == tableFood.seats &&
                windowView == tableFood.windowView &&
                floor == tableFood.floor &&
                Objects.equals(id, tableFood.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableNr, seats, windowView, floor, commands, reservations);
    }

    @Override
    public String toString() {
        return "TableFood{" +
                "id=" + id +
                ", tableNr=" + tableNr +
                ", seats=" + seats +
                ", windowView=" + windowView +
                ", floor=" + floor +
                '}';
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
