package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.service.MenuItemService;
import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping(value = "/menuItem")
public class MenuItemResource {

    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<MenuItemDto> getMenuItems() {
        return menuItemService.getMenuItems();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addMenuItem(@NotNull @RequestBody  MenuItemDto menuItemDto) {
        menuItemService.addMenuItem(menuItemDto);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public void updateMenuItem(@RequestBody @NotNull MenuItemDto menuItemDto) {
        menuItemService.updateMenuItem(menuItemDto);
    }

    @RequestMapping(path = "/remove/{menuItemName}", method = RequestMethod.DELETE)
    public void removeMenuItem(@PathVariable String menuItemName) {
        menuItemService.removeMenuItem(menuItemName);
    }
}
