package ro.quickorder.backend.model;


import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class MenuItemType {
    @Id
    private Long id;
    private String type;

    @OneToMany(mappedBy = "menuItemType")
    private List<MenuItem> menuItems;

    public MenuItemType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public MenuItemType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemType that = (MenuItemType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(menuItems, that.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, menuItems);
    }

    @Override
    public String toString() {
        return "MenuItemType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
