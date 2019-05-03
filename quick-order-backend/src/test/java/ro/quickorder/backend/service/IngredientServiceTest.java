package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.repository.IngredientRepository;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author R. Lupoaie
 */
@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientServiceTest {

    @Autowired
    IngredientService ingredientService;
    @Autowired
    IngredientRepository ingredientRepository;


    @Before
    public void setUp() {
        Ingredient ingredient1 = new Ingredient("muraturi");
        Ingredient ingredient2 = new Ingredient("cartofi");
        Ingredient ingredient3 = new Ingredient("lapte");
        Ingredient ingredient4 = new Ingredient("salam");

        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);
        ingredientRepository.save(ingredient4);
    }


    @After
    public void tearDown() {
        ingredientRepository.deleteAll();
    }

    @Test
    public void testGetAll() {
        List<IngredientDto> ingredientDtos = ingredientService.getAll();

        assertEquals(4, ingredientDtos.size());
    }

    @Test
    public void testAddIngredient() {
        List<IngredientDto> ingredientDtos = ingredientService.getAll();
        assertEquals(4, ingredientDtos.size());

        IngredientDto ingredientDto = new IngredientDto("afine");
        ingredientService.addIngredient(ingredientDto);

        List<IngredientDto> ingredientDtosAfter = ingredientService.getAll();
        assertEquals(5, ingredientDtosAfter.size());
    }

    @Test
    public void testAddIngredientWithIngredientNameToShort() {
        try{
            IngredientDto ingredientDto = new IngredientDto("e");
            ingredientService.addIngredient(ingredientDto);
        }catch (BadRequestException e){
            assertEquals(e.getMessage(), "Ingredient name is to short!");
        }
    }

    @Test
    public void testAddIngredientWithIngredientThatAlreadyExists() {
        try{
            IngredientDto ingredientDto = new IngredientDto("muraturi");
            ingredientService.addIngredient(ingredientDto);
        }catch (NotFoundException e){
            assertEquals(e.getMessage(), "Ingredient already exists!");
        }
    }

}