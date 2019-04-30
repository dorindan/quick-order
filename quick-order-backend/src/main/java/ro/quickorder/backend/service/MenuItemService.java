package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.repository.IngredientRepository;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author R. Lupoaie
 */
@Service
public class MenuItemService {
    private static final Logger LOG = LoggerFactory.getLogger(MenuItemService.class);
    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuItemTypeRepository menuItemTypeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private MenuItemConverter menuItemConverter;
    @Autowired
    private MenuItemTypeConverter menuItemTypeConverter;

    public List<MenuItemDto> getMenuItems() {
        return menuItemRepository.findAll().stream().map(menuItemConverter::toMenuItemDto).collect(Collectors.toList());
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
        MenuItemType menuItemType = menuItemTypeRepository.findByType(menuItemDto.getMenuItemTypeDto().getType());
        if (menuItemType == null) {
            LOG.error("MenuItemType was not found!");
            throw new NotFoundException("MenuItemType was not found!");
        }

        Set<Ingredient> ingredients = setIngredients(menuItemDto);
        menuItem = new MenuItem(menuItemDto.getName(), menuItemDto.getDescription(), menuItemType, menuItemDto.getPrice(), menuItemDto.getPreparationDurationInMinutes(), ingredients);
        menuItemRepository.save(menuItem);
    }

    public void updateMenuItem(MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findByName(menuItemDto.getName());
        if (menuItem == null) {
            LOG.error("MenuItem not found!");
            throw new NotFoundException("MenuItem not found!");
        }
        MenuItemType menuItemType = menuItemTypeRepository.findByType(menuItemDto.getMenuItemTypeDto().getType());
        if (menuItemType == null) {
            LOG.error("MenuItemType was not found!");
            throw new NotFoundException("MenuItemType was not found!");
        }

        Set<Ingredient> ingredients = setIngredients(menuItemDto);
        menuItem.setIngredients(ingredients);
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setMenuItemType(menuItemType);
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());
        menuItemRepository.save(menuItem);
    }

    private Set<Ingredient> setIngredients(MenuItemDto menuItemDto) {
        Set<Ingredient> ingredients = new HashSet<>();
        if (menuItemDto.getIngredients() != null) {
            menuItemDto.getIngredients().forEach(ingredientDto -> {
                Ingredient ingredient = ingredientRepository.findFirstByName(ingredientDto.getName());
                if (ingredient != null) {
                    ingredients.add(ingredient);
                } else {
                    LOG.error("Ingredient " + ingredient.toString() + " was not found!");
                    throw new NotFoundException("Ingredient " + ingredient.toString() + " was not found!");
                }
            });
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
