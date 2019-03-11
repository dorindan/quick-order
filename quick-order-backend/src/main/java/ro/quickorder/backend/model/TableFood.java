package ro.quickorder.backend.model;



import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class TableFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int tableNr;
    private int seats;
    private boolean windowView;
    private int floor;
    private boolean free;

    @OneToMany(mappedBy = "table")
    private List<Command> commands;

    @ManyToMany(mappedBy = "tables")
    private List<Reservation> reservations;

    public TableFood(int tableNr, int seats, boolean windowView, int floor, boolean free) {
        this.tableNr = tableNr;
        this.seats = seats;
        this.windowView = windowView;
        this.floor = floor;
        this.free = free;
    }

    public TableFood() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
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
                free == tableFood.free &&
                Objects.equals(id, tableFood.id) &&
                Objects.equals(commands, tableFood.commands) &&
                Objects.equals(reservations, tableFood.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tableNr, seats, windowView, floor, free, commands, reservations);
    }

    @Override
    public String toString() {
        return "TableFood{" +
                "id=" + id +
                ", tableNr=" + tableNr +
                ", seats=" + seats +
                ", windowView=" + windowView +
                ", floor=" + floor +
                ", free=" + free +
                '}';
    }
}
