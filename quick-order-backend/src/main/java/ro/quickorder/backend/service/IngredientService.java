package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.IngredientConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.repository.IngredientRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author R. Lupoaie
 */
@Service
public class IngredientService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    IngredientConverter ingredientConverter;

    public List<IngredientDto> getAll() {
        return ingredientRepository.findAll().stream().map(ingredientConverter::toIngredientDto).collect(Collectors.toList());
    }

    public void addIngredient(IngredientDto ingredientDto) {
        if (ingredientDto.getName().length() < 2) {
            LOG.error("Ingredient name is to short!");
            throw new BadRequestException("Ingredient name is too short!");
        }

        if (ingredientRepository.existsIngredientByName(ingredientDto.getName())) {
            LOG.error("Ingredient already exists!");
            throw new NotFoundException("Ingredient already exists!");
        }
        Ingredient ingredient = ingredientConverter.toIngredient(ingredientDto);
        ingredientRepository.save(ingredient);
    }
}
