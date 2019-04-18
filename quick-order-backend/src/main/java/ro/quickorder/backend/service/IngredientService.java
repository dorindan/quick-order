package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.IngredientConverter;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.repository.IngredientRepository;

import java.util.List;
import java.util.stream.Collectors;

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
        return ingredientRepository.findAll().stream().map(ingredientConverter::toIngredientDto).collect(Collectors.toList());
    }
}
