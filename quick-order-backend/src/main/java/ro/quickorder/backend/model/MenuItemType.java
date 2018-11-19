package ro.quickorder.backend.model;


import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class MenuItemType {
    private Long id;
    private String type;

    @OneToMany(mappedBy = "menuItemType")
    private List<MenuItem> menuItemList;

}
