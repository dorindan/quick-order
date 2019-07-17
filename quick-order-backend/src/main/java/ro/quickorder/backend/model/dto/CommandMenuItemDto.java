package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.MenuItem;

/**
 * @author R. Lupoaie
 */
public class CommandMenuItemDto {

    private Integer amount;
    private MenuItemDto menuItemDto;


    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public MenuItemDto getMenuItemDto() {
        return menuItemDto;
    }

    public void setMenuItemDto(MenuItemDto menuItemDto) {
        this.menuItemDto = menuItemDto;
    }
}
