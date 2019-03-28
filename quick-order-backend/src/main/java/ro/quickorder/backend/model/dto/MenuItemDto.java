package ro.quickorder.backend.model.dto;

/**
 *  Data transfer object for {@link ro.quickorder.backend.model.MenuItem}
 *
 *  *@author R. Lupoaie
 */
public class MenuItemDto {

    private String name;
    private String description;
    private Integer preparationDurationInMinutes;

    public MenuItemDto() {

    }

    public MenuItemDto(String name, String description, Integer preparationDurationInMinutes) {
        this.name = name;
        this.description = description;
        this.preparationDurationInMinutes = preparationDurationInMinutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreparationDurationInMinutes() {
        return preparationDurationInMinutes;
    }

    public void setPreparationDurationInMinutes(Integer preparationDurationInMinutes) {
        this.preparationDurationInMinutes = preparationDurationInMinutes;
    }
}
