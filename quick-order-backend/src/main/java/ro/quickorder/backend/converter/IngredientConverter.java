package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;

import java.util.ArrayList;
import java.util.List;

/**
 Converts Commands to their corresponding DTO and vice versa.
 *@author R. Lupoaie
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

    public List<Ingredient> toIngredientList(List<IngredientDto> ingredientDtos) {
        if (ingredientDtos == null) {
            return null;
        }
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredientDtos.forEach(ingredientDto -> ingredients.add(toIngredient(ingredientDto)) );
        return ingredients;
    }

    public List<IngredientDto> toIngredientDtoList(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        ArrayList<IngredientDto> ingredientDtos = new ArrayList<>();
        ingredients.forEach(ingredient -> ingredientDtos.add(toIngredientDto(ingredient)) );
        return ingredientDtos;
    }



}
