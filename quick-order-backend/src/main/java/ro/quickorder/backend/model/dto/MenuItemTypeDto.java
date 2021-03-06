package ro.quickorder.backend.model.dto;

/**
 * @author R. Lupoaie
 */
public class MenuItemTypeDto {

    private String type;

    public MenuItemTypeDto() {
    }

    public MenuItemTypeDto(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
