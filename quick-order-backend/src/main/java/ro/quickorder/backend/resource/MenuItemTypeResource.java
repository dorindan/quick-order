package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.List;

@RestController
public class MenuItemTypeResource {
    @Autowired
    MenuItemTypeRepository menuItemTypeRepository;

    @RequestMapping(path = "/menuItemType/all", method = RequestMethod.GET)
    public List<MenuItemType> getAllMenuItemTypes() {
        return menuItemTypeRepository.findAll();
    }

}
