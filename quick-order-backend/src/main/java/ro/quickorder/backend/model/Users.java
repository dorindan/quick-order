package ro.quickorder.backend.model;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
public class Users {

    @Id
    private int id;

    @OneToMany(mappedBy = "user")
    private List<Feedback> feedback;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservationList;

    @ManyToMany
    @JoinTable(name = "user_command",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "command_id") })
    private List<Command> commandList;


}
