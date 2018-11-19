package ro.quickorder.backend.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class TableFood {
    @Id
    private int id;
    private int tableNr;
    private int seats;
    private boolean windowView;
    private int floor;
    private boolean free;

    @OneToMany(mappedBy = "table")
    private List<Command> commandList;

    @ManyToMany(mappedBy = "tableList")
    private List<Reservation> reservationList;


}
