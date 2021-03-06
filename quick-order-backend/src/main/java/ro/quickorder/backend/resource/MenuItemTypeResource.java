package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.service.MenuItemTypeService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/api/menuItemType")
public class MenuItemTypeResource {
    @Autowired
    MenuItemTypeService menuItemTypeService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasRole('WAITER') or hasRole('USER')")
    public List<MenuItemTypeDto> getAllMenuItemTypes() {
        return menuItemTypeService.getAllMenuItemTypes();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('WAITER')")
    public void addMenuItem(@NotNull @RequestBody MenuItemTypeDto menuItemTypeDto) {
        menuItemTypeService.addMenuItemType(menuItemTypeDto);
    }
}
