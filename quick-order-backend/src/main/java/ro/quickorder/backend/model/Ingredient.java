package ro.quickorder.backend.model;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

public class Ingredient {
    @Id
    private int id;
    private String name;

   @ManyToMany(mappedBy = "ingredientList")
    private List<MenuItem> menuItemList;
}
