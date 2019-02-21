package ro.quickorder.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ro.quickorder.backend.service.CustomDateDeserializer;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Timestamp checkInTime;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Timestamp checkOutTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private Command command;

    private Integer numberOfPersons;
    private boolean confirmed;
    private String status;


    @ManyToMany
    @JoinTable(name = "table_reservation",
            joinColumns = { @JoinColumn(name = "reservation_id") },
            inverseJoinColumns = { @JoinColumn(name = "table_id") })
    private List<TableFood> tables;

    public Reservation(Timestamp checkInTime, Timestamp checkOutTime, User user, Command command, int numberOfPersons, boolean confirmed, String status, List<TableFood> tables) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.user = user;
        this.command = command;
        this.numberOfPersons = numberOfPersons;
        this.confirmed = confirmed;
        this.status = status;
        this.tables = tables;
    }

    public Reservation(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
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
                Objects.equals(checkInTime, that.checkInTime) &&
                Objects.equals(checkOutTime, that.checkOutTime) &&
                Objects.equals(user, that.user) &&
                Objects.equals(command, that.command) &&
                Objects.equals(status, that.status) &&
                Objects.equals(tables, that.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkInTime, checkOutTime, user, command, confirmed, status, tables);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", checkInTime=" + checkInTime +
                ", checkOutTime=" + checkOutTime +
                ", command=" + command +
                ", confirmed=" + confirmed +
                ", status='" + status + '\'' +
                '}';
    }
}

