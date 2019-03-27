package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.MenuItemTypeRepository;
import ro.quickorder.backend.service.MenuItemTypeService;

import java.util.List;


@RestController
@RequestMapping(value = "/menuItemType")
public class MenuItemTypeResource {
    @Autowired
    MenuItemTypeService menuItemTypeService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<MenuItemTypeDto> getAllMenuItemTypes() {
        return menuItemTypeService.getAllMenuItemTypes();
    }

}
