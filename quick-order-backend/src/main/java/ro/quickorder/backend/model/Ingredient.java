package ro.quickorder.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
@Entity
public class Ingredient {
    @Id
    private Long id;
    private String name;

   @ManyToMany(mappedBy = "ingredientList")
    private List<MenuItem> menuItemList;

}
