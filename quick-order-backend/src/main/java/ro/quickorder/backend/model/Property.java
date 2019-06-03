package ro.quickorder.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    private PropertyName name;

    private String value;

    public Property(){

    }

    public Property(PropertyName name, String value){
        this.name = name;
        this.value = value;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property that = (Property) o;
        return Objects.equals(id, that.id) &&
                name == that.name &&
                value.equals(that.value) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }


    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", numeRestaurant='" + name + '\'' +
                ", startProgramTime=" + value +
                '}';
    }

    public PropertyName getName() {
        return name;
    }

    public void setName(PropertyName name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
