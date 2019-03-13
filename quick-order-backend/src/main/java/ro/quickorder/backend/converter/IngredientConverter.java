package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;

@Component
public class IngredientConverter {

    public Ingredient convertIngredientDtoToIngredient(IngredientDto ingredientDto){
        Ingredient ingredient= new Ingredient();
        ingredient.setName(ingredientDto.getName());
        return ingredient;
    }

    public IngredientDto convertIngredientToIngredientDto(Ingredient ingredient){
        IngredientDto ingredientDto= new IngredientDto();
        ingredientDto.setName(ingredient.getName());
        return ingredientDto;
    }

}
