package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.repository.BillRepository;
import ro.quickorder.backend.repository.MenuItemRepository;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class MenuItemResource {
    @Autowired
    MenuItemRepository menuItemRepository;

    @RequestMapping(path = "/menuItemIngredients", method = RequestMethod.GET)
    public List<Ingredient> getIngredients(@RequestParam(value = "id", defaultValue = "1") Long id) {
        return menuItemRepository.findById(id).get().getIngredients();
    }

    @RequestMapping(path = "/menuItem", method = RequestMethod.GET)
    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }
}
