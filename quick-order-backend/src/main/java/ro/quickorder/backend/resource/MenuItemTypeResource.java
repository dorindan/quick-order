package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.service.MenuItemTypeService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/menuItemType")
public class MenuItemTypeResource {
    @Autowired
    MenuItemTypeService menuItemTypeService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<MenuItemTypeDto> getAllMenuItemTypes() {
        return menuItemTypeService.getAllMenuItemTypes();
    }
}
