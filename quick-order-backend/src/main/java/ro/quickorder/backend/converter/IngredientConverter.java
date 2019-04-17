package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;

import java.util.HashSet;
import java.util.Set;

/**
 * Converts Ingredients to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */
@Component
public class IngredientConverter {

    public Ingredient toIngredient(IngredientDto ingredientDto) {
        if (ingredientDto == null) {
            return null;
        }
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDto.getName());
        return ingredient;
    }

    public IngredientDto toIngredientDto(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(ingredient.getName());
        return ingredientDto;
    }

    public Set<Ingredient> toIngredientList(Set<IngredientDto> ingredientDtos) {
        if (ingredientDtos == null) {
            return null;
        }
        Set<Ingredient> ingredients = new HashSet<>();
        ingredientDtos.forEach(ingredientDto -> ingredients.add(toIngredient(ingredientDto)));
        return ingredients;
    }

    public Set<IngredientDto> toIngredientDtoList(Set<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        Set<IngredientDto> ingredientDtos = new HashSet<>();
        ingredients.forEach(ingredient -> ingredientDtos.add(toIngredientDto(ingredient)));
        return ingredientDtos;
    }
}
