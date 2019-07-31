package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.CommandDto;
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

    @RequestMapping(path = "/all-from-command", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or hasRole('WAITER')")
    public CommandDto updateMenuItemsFromCommand(CommandDto commandDto) {
        return menuItemService.updateMenuItemsFromCommand(commandDto);
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('WAITER')")
    public void addMenuItem(@NotNull @RequestBody MenuItemDto menuItemDto) {
        menuItemService.addMenuItem(menuItemDto);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('WAITER')")
    public void updateMenuItem(@RequestBody MenuItemDto menuItemDto) {
        menuItemService.updateMenuItem(menuItemDto);
    }

    @RequestMapping(path = "/updateImg", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('WAITER')")
    public void updateMenuItem(@RequestParam("file") MultipartFile file) {
        menuItemService.uploadImg(file);
    }

    @RequestMapping(path = "/remove/{menuItemName}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('WAITER')")
    public void removeMenuItem(@PathVariable String menuItemName) {
        menuItemService.removeMenuItem(menuItemName);
    }
}
