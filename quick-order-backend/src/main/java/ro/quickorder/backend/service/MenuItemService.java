package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.exception.NotFoundException;
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

    public void addMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if(menuItem != null){
            LOG.error("MenuItem already exists!");
            throw new NotFoundException("MenuItem already exists!");
        }
        menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setIngredients(menuItemDto.getIngredients());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemDto.getMenuItemType());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());

        menuItemRepository.save(menuItem);
    }

    public void updateMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if(menuItem == null){
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        menuItem.setIngredients(menuItemDto.getIngredients());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemDto.getMenuItemType());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());

        menuItemRepository.save(menuItem);
    }

    public void removeMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if(menuItem == null){
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        menuItemRepository.delete(menuItem);
    }

}
