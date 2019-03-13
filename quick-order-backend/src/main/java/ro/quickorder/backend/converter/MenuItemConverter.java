package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.MenuItemDto;

@Component
public class MenuItemConverter {

    public MenuItem convertMenuItemDtoToMenuItem(MenuItemDto menuItemDtoDto){
        MenuItem menuItem= new MenuItem();
        menuItem.setName(menuItemDtoDto.getName());
        menuItem.setDescription(menuItemDtoDto.getDescription());
        menuItem.setPreparationDurationInMinutes(menuItemDtoDto.getPreparationDurationInMinutes());
        return menuItem;
    }

    public MenuItemDto convertMenuItemToMenuItemDto(MenuItem menuItem){
        MenuItemDto menuItemDto= new MenuItemDto();
        menuItemDto.setName(menuItem.getName());
        menuItemDto.setDescription(menuItem.getDescription());
        menuItemDto.setPreparationDurationInMinutes(menuItem.getPreparationDurationInMinutes());
        return menuItemDto;
    }


}
