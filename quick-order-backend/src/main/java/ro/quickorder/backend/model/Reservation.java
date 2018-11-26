package ro.quickorder.backend.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    private Long id;
    private Timestamp timeCheckIn;
    private Timestamp timeCheckOut;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private Command command;

    private boolean confirmed;
    private String status;


    @ManyToMany
    @JoinTable(name = "table_reservation",
            joinColumns = { @JoinColumn(name = "reservation_id") },
            inverseJoinColumns = { @JoinColumn(name = "table_id") })
    private List<TableFood> tables;

    public Reservation(Long id, Timestamp timeCheckIn, Timestamp timeCheckOut, Users user, Command command, boolean confirmed, String status) {
        this.id = id;
        this.timeCheckIn = timeCheckIn;
        this.timeCheckOut = timeCheckOut;
        this.user = user;
        this.command = command;
        this.confirmed = confirmed;
        this.status = status;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeCheckIn() {
        return timeCheckIn;
    }

    public void setTimeCheckIn(Timestamp timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public Timestamp getTimeCheckOut() {
        return timeCheckOut;
    }

    public void setTimeCheckOut(Timestamp timeCheckOut) {
        this.timeCheckOut = timeCheckOut;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TableFood> getTables() {
        return tables;
    }

    public void setTables(List<TableFood> tables) {
        this.tables = tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return confirmed == that.confirmed &&
                Objects.equals(id, that.id) &&
                Objects.equals(timeCheckIn, that.timeCheckIn) &&
                Objects.equals(timeCheckOut, that.timeCheckOut) &&
                Objects.equals(user, that.user) &&
                Objects.equals(command, that.command) &&
                Objects.equals(status, that.status) &&
                Objects.equals(tables, that.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeCheckIn, timeCheckOut, user, command, confirmed, status, tables);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", timeCheckIn=" + timeCheckIn +
                ", timeCheckOut=" + timeCheckOut +
                ", user=" + user +
                ", command=" + command +
                ", confirmed=" + confirmed +
                ", status='" + status + '\'' +
                '}';
    }
}

