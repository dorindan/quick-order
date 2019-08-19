package ro.quickorder.backend.model.dto;

/**
 * @author R. Lupoaie
 */
public class MenuItemCommandDto {

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
