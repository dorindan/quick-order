package ro.quickorder.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Command {
    @Id
    private Long id;
    private String commandName;
    private String specification;
    private boolean isPacked;
    private String status;

    @ManyToMany(mappedBy = "commandList")
    private List<MenuItem> menuItemList;

    @OneToOne
    @JoinColumn(name="bill_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableFood table;

    @OneToMany(mappedBy = "command")
    private List<Reservation> reservationList;

    @ManyToMany(mappedBy = "commandList")
    private List<Users> userList;


}
