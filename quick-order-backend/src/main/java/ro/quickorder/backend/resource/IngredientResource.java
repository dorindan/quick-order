package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.service.IngredientService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/ingredient")
public class IngredientResource {
    @Autowired
    IngredientService ingredientService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public List<IngredientDto> getAll() {
        return ingredientService.getAll();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('WAITER')")
    public void addMenuItem(@NotNull @RequestBody IngredientDto ingredientDto) {
        ingredientService.addIngredient(ingredientDto);
    }
}
