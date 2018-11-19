package ro.quickorder.backend.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
public class Reservation {

    @Id
    private int id;
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
    private List<Table> tableList;


}

