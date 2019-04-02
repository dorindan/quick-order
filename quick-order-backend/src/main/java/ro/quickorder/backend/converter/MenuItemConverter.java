package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.model.dto.MenuItemDto;

import java.util.Arrays;
import java.util.List;

/**
 *Converts Commands to their corresponding DTO and vice versa.
 *
 *@author R. Lupoaie
 */
@Component
public class MenuItemConverter {

    @Autowired
    private IngredientConverter ingredientConverter;

    public MenuItem toMenuItem(MenuItemDto menuItemDto) {
        if (menuItemDto == null) {
            return null;
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPreparationDurationInMinutes(menuItemDto.getPreparationDurationInMinutes());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setIngredients(ingredientConverter.toIngredientList(Arrays.asList( menuItemDto.getIngredients())));
        menuItem.setMenuItemType(menuItemDto.getMenuItemTypeDto());
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
        List<IngredientDto> ingredientDtos = ingredientConverter.toIngredientDtoList(menuItem.getIngredients());
        IngredientDto[] ingredientDtosConverted = new IngredientDto[ingredientDtos.size()];
        for(int i=0;i<ingredientDtos.size();i++){
            ingredientDtosConverted[i] = ingredientDtos.get(i);
        }
        menuItemDto.setIngredients(ingredientDtosConverted);
        menuItemDto.setMenuItemTypeDto(menuItem.getMenuItemType());
        return menuItemDto;
    }

}
