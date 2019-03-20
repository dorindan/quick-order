package ro.quickorder.backend.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class MenuItemType {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    private String type;

    @OneToMany(mappedBy = "menuItemType")
    private List<MenuItem> menuItems;

    public MenuItemType(String type) {
        this.type = type;
    }

    public MenuItemType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
