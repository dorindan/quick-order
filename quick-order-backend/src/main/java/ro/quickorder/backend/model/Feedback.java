package ro.quickorder.backend.model;

import javax.persistence.*;

@Entity
public class Feedback {
    @Id
    private Long id;
    private int rating;
    private String message;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;


}
