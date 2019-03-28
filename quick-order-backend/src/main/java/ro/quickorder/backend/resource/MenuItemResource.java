package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.repository.BillRepository;
import ro.quickorder.backend.repository.MenuItemRepository;

import javax.ws.rs.PathParam;
import java.util.List;


@RestController
public class MenuItemResource {
    @Autowired
    MenuItemRepository menuItemRepository;

    @RequestMapping(path = "/menuItem/{id}/ingredients", method = RequestMethod.GET)
    public List<Ingredient> getIngredients(@PathVariable("id") Long id) {
        return menuItemRepository.findById(id).get().getIngredients();
    }

    @RequestMapping(path = "/menuItem/all", method = RequestMethod.GET)
    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }
}
