package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.repository.IngredientRepository;
import ro.quickorder.backend.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping(value = "/ingredient")
public class IngredientResource {
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    IngredientService ingredientService;

    @RequestMapping(path = "/ingredient", method = RequestMethod.GET)
    public String findByName(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ingredientRepository.findFirstByName(name).toString();
    }



    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<IngredientDto> getAll() {
        return ingredientService.getAll();
    }


}
