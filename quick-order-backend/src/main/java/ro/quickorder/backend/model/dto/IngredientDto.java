package ro.quickorder.backend.model.dto;

/**
 *  Data transfer object for {@link ro.quickorder.backend.model.Ingredient}
 *
 *  *@author R. Lupoaie
 */
public class IngredientDto {

    private String name;

    public IngredientDto() {

    }

    public IngredientDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
