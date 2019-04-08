package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.IngredientConverter;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author R. Lupoaie
 */
@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    IngredientConverter ingredientConverter;


    public List<IngredientDto> getAll() {
        List<IngredientDto> ingredientDtos = new ArrayList<>();

        List<Ingredient> ingredients = ingredientRepository.findAll();

        for(Ingredient ingredient: ingredients)
        {
            ingredientDtos.add(ingredientConverter.toIngredientDto(ingredient));
        }

        return ingredientDtos;
    }
}
