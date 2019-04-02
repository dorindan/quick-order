package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.IngredientConverter;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.repository.IngredientRepository;
import ro.quickorder.backend.repository.MenuItemRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private IngredientRepository ingredientRepository;
    @Autowired
    private MenuItemConverter menuItemConverter;

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

        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDto ingredientDto : menuItemDto.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findFirstByName(ingredientDto.getName());
            if (ingredient != null) {
                ingredients.add(ingredient);
            }
            else{
                LOG.error("Item was not found!");
                throw new NotFoundException("Item was not found!");
            }
        }

        menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setIngredients(ingredients);
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemDto.getMenuItemType());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());

        System.out.println("get all was called ");
        menuItemRepository.save(menuItem);
    }

    public void updateMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if (menuItem == null) {
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        ArrayList<IngredientDto> ingredientDtos = (ArrayList)Arrays.asList(menuItemDto.getIngredients());
        menuItem.setIngredients(ingredientConverter.toIngredientList(ingredientDtos));
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemDto.getMenuItemType());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());

        menuItemRepository.save(menuItem);
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
