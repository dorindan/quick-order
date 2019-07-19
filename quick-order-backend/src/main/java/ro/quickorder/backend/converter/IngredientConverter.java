package ro.quickorder.backend.converter;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;

import java.util.Set;
import java.util.stream.Collectors;

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
        return ingredientDtos.stream().map(this::toIngredient).collect(Collectors.toSet());
    }

    public Set<IngredientDto> toIngredientDtoList(Set<Ingredient> ingredients) {
        if (ingredients == null || !Hibernate.isInitialized(ingredients)) {
            return null;
        }
        return ingredients.stream().map(this::toIngredientDto).collect(Collectors.toSet());
    }
}
