package ro.quickorder.backend.model.dto;


import ro.quickorder.backend.model.enumeration.CommandStatus;

import java.util.List;

/**
 *  Data transfer object for {@link ro.quickorder.backend.model.Command}
 * <p>
 *
 * @author R. Lupoaie
 */
public class CommandDto {
    private String commandName;
    private String specification;
    private boolean isPacked;
    private CommandStatus status;
    private List<MenuItemCommandDto> MenuItemCommandDtos;

    public CommandDto() {
    }

    public CommandDto(String commandName, String specification, boolean isPacked, CommandStatus status) {
        this.commandName = commandName;
        this.specification = specification;
        this.isPacked = isPacked;
        this.status = status;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public boolean isPacked() {
        return isPacked;
    }

    public void setPacked(boolean packed) {
        isPacked = packed;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public List<MenuItemCommandDto> getMenuItemCommandDtos() {
        return MenuItemCommandDtos;
    }

    public void setMenuItemCommandDtos(List<MenuItemCommandDto> menuItemCommandDtos) {
        this.MenuItemCommandDtos = menuItemCommandDtos;
    }
}
