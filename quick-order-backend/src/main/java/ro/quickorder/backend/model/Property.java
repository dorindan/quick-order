package ro.quickorder.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalTime;

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

    private LocalTime startProgramTime;

    private LocalTime endProgramTime;

    public Property() {
    }

    public Property(LocalTime startProgramTime, LocalTime endProgramTime) {
        this.startProgramTime = startProgramTime;
        this.endProgramTime = endProgramTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getStartProgramTime() {
        return startProgramTime;
    }

    public void setStartProgramTime(LocalTime startProgramTime) {
        this.startProgramTime = startProgramTime;
    }

    public LocalTime getEndProgramTime() {
        return endProgramTime;
    }

    public void setEndProgramTime(LocalTime endProgramTime) {
        this.endProgramTime = endProgramTime;
    }


    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", startProgramTime=" + startProgramTime +
                ", endProgramTime=" + endProgramTime +
                '}';
    }

}
