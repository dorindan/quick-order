package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.service.MenuItemService;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping(value = "/api/menuItem")
public class MenuItemResource {
    @Autowired
    private MenuItemService menuItemService;

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public List<MenuItemDto> getMenuItems() {
        return menuItemService.getMenuItems();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('WAITER')")
    public void addMenuItem(@NotNull @RequestBody MenuItemDto menuItemDto) {
        menuItemService.addMenuItem(menuItemDto);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('WAITER')")
    public void updateMenuItem(@RequestBody @NotNull MenuItemDto menuItemDto) {
        System.out.println(menuItemDto.getImg() instanceof File);
        menuItemService.updateMenuItem(menuItemDto);
    }

    @RequestMapping(path = "/remove/{menuItemName}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('WAITER')")
    public void removeMenuItem(@PathVariable String menuItemName) {
        menuItemService.removeMenuItem(menuItemName);
    }
}
