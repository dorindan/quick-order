package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;

import static org.junit.Assert.*;

/**
 * Unit test for {@link IngredientConverter}
 *
 * @author R. Lupoaie
 */
public class IngredientConverterTest {

    private IngredientConverter ingredientConverter = new IngredientConverter();

    @Test
    public void testConvertIngredientToDto(){
        Ingredient ingredient = new Ingredient("name1");

        IngredientDto ingredientDto = ingredientConverter.toIngredientDto(ingredient);

        assertEquals(ingredientDto.getName(),ingredient.getName());
        assertEquals(ingredientDto.getName(),ingredient.getName());
    }
    @Test
    public void testConvertIngredientToDtoWhenIngredientIsNull(){
        IngredientDto ingredientDto = ingredientConverter.toIngredientDto(null);

        assertNull(ingredientDto);
    }

    @Test
    public void testConvertDtoToIngredient(){
        IngredientDto ingredientDto = new IngredientDto("name1");

        Ingredient ingredient = ingredientConverter.toIngredient(ingredientDto);

        assertEquals(ingredientDto.getName(),ingredient.getName());
        assertEquals(ingredientDto.getName(),ingredient.getName());
        assertNull(ingredient.getMenuItems());
    }
    @Test
    public void testConvertDtoToIngredientWhenDtoIsNull(){
        Ingredient ingredient = ingredientConverter.toIngredient(null);

        assertNull(ingredient);
    }




}