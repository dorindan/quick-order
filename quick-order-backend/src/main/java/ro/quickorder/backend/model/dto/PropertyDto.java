package ro.quickorder.backend.model.dto;

import javax.ws.rs.Produces;
import java.time.LocalTime;

/**
 * @author R. Lupoaie
 */
public class PropertyDto {

    private String name;
    private String value;

    public PropertyDto(){

    }

    public PropertyDto(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}