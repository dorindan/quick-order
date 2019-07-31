package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.model.dto.MenuItemDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Converts MenuItems to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */
@Component
public class MenuItemConverter {

    @Autowired
    private IngredientConverter ingredientConverter;
    @Autowired
    private MenuItemTypeConverter menuItemTypeConverter;

    public MenuItemConverter() {

    }

    public MenuItemConverter(IngredientConverter ingredientConverter, MenuItemTypeConverter menuItemTypeConverter) {
        this.ingredientConverter = ingredientConverter;
        this.menuItemTypeConverter = menuItemTypeConverter;
    }

    public MenuItem toMenuItem(MenuItemDto menuItemDto) {
        if (menuItemDto == null) {
            return null;
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());
        menuItem.setPrice(menuItemDto.getPrice());
        if (menuItemDto.getIngredients() == null) {
            menuItem.setIngredients(null);
        } else {
            menuItem.setIngredients(ingredientConverter.toIngredientList(menuItemDto.getIngredients()));
        }
        menuItem.setMenuItemType(menuItemTypeConverter.toMenuItemType(menuItemDto.getMenuItemTypeDto()));
        return menuItem;
    }

    public MenuItemDto toMenuItemDto(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setName(menuItem.getName());
        menuItemDto.setDescription(menuItem.getDescription());
        menuItemDto.setPreparationDurationInMinutes(menuItem.getPreparationDurationInMinutes());
        menuItemDto.setPrice(menuItem.getPrice());
        if (menuItem.getIngredients() == null) {
            menuItemDto.setIngredients(null);
        } else {
            Set<IngredientDto> ingredientDtoSet = ingredientConverter.toIngredientDtoList(menuItem.getIngredients());
            menuItemDto.setIngredients(ingredientDtoSet);
        }
        menuItemDto.setMenuItemTypeDto(menuItemTypeConverter.toMenuItemTypeDto(menuItem.getMenuItemType()));
        final String dir = System.getProperty("user.dir");
        String location = "\\src\\assets\\menuItemImg\\";
        String orgName = menuItemDto.getName()  ;
        String filePath = dir + location + orgName + ".jpg";
        File dest = new File(filePath);
        if (dest.exists() && !dest.isDirectory()) {
            menuItemDto.setImg(true);
        }
        return menuItemDto;
    }
}
