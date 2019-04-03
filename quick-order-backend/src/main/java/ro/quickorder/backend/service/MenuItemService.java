package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.IngredientConverter;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.repository.IngredientRepository;
import ro.quickorder.backend.repository.MenuItemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author R. Lupoaie
 */
@Service
public class MenuItemService {

    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private MenuItemConverter menuItemConverter;
    @Autowired
    private MenuItemTypeConverter menuItemTypeConverter;

    @Autowired
    private IngredientConverter ingredientConverter;

    public List<MenuItemDto> getMenuItems() {
        List<MenuItemDto> result = new ArrayList<>();
        List<MenuItem> menuItems = menuItemRepository.findAll();

        for (MenuItem menuItem : menuItems) {
            result.add(menuItemConverter.toMenuItemDto(menuItem));
        }
        return result;
    }

    public void addMenuItem(MenuItemDto menuItemDto) {
        if (menuItemDto.getName() == null) {
            LOG.error("Name can not be null");
            throw new NotFoundException("Name can not be null");
        }
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if (menuItem != null) {
            LOG.error("MenuItem already exists!");
            throw new NotFoundException("MenuItem already exists!");
        }

        List<Ingredient> ingredients = setIngredients(menuItemDto);

        menuItem = new MenuItem(menuItemDto.getName(), menuItemDto.getDescription(), menuItemDto.getPrice(), menuItemDto.getPreparationDurationInMinutes(), ingredients);
        menuItemRepository.save(menuItem);
    }

    public void updateMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if (menuItem == null) {
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }


        List<Ingredient> ingredients = setIngredients(menuItemDto);

        menuItem.setIngredients(ingredients);
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemTypeConverter.toMenuItemType(menuItemDto.getMenuItemTypeDto()));
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());

        menuItemRepository.save(menuItem);
    }

    private List<Ingredient> setIngredients(MenuItemDto menuItemDto) {
        List<Ingredient> ingredients = new ArrayList<>();
        if (menuItemDto.getIngredients() != null)
            for (int i = 0; i < menuItemDto.getIngredients().length; i++) {
                Ingredient ingredient = ingredientRepository.findFirstByName(menuItemDto.getIngredients()[i].getName());
                if (ingredient != null) {
                    ingredients.add(ingredient);
                } else {
                    LOG.error("Ingredient was not found!");
                    throw new NotFoundException("Ingredient was not found!");
                }
            }
        return ingredients;
    }

    public void removeMenuItem(String menuItemName) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemName);
        if (menuItem == null) {
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        menuItemRepository.delete(menuItem);
    }

}
