package ro.quickorder.backend.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class MenuItem {
    @Id
    private int id;
    private String name;
    private String description;
    private int preparationTime;

    @ManyToOne
    @JoinColumn(name = "menu_item_type_id")
    private MenuItemType menuItemType;

    @OneToMany(mappedBy = "menuItem")
    private List<Feedback> feedbackList;

    @ManyToMany
    @JoinTable(name = "menu_item_ingredient",
            joinColumns = { @JoinColumn(name = "menu_item_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") })
    private List<Ingredient> ingredientList;

    @ManyToMany
    @JoinTable(name = "menu_item_command",
            joinColumns = { @JoinColumn(name = "menu_item_id") },
            inverseJoinColumns = { @JoinColumn(name = "command_id") })
    private List<Command> commandList;


}
