package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.service.IngredientService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/ingredient")
public class IngredientResource {
    @Autowired
    IngredientService ingredientService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<IngredientDto> getAll() {
        return ingredientService.getAll();
    }
}
