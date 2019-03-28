package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.repository.MenuItemRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author R. Lupoaie
 */
@Service
public class MenuItemService {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuItemConverter menuItemConverter;

    public List<MenuItemDto> getMenuItems() {
        List<MenuItemDto> result = new ArrayList<>();
        List<MenuItem> menuItems =  menuItemRepository.findAll();

        for(MenuItem menuItem: menuItems){
            result.add(menuItemConverter.toMenuItemDto(menuItem));
        }

        return  result;
    }

    public void addMenuItem() {
        menuItemRepository.findAll();
    }

    public void updateMenuItem() {
        menuItemRepository.findAll();
    }

    public void removeMenuItem() {
        menuItemRepository.findAll();
    }

}
