package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.repository.IngredientRepository;

@RestController
public class IngredientResource {
    @Autowired
    IngredientRepository ingredientRepository;

        @RequestMapping(path = "/ingredient", method = RequestMethod.GET )
    public Ingredient findByName(@RequestParam(value="name", defaultValue="World")String name){
        return ingredientRepository.findFirstByName(name);
    }
}
