package ro.quickorder.backend.model.dto;

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
    private String status;

    public CommandDto() {
    }

    public CommandDto(String commandName, String specification, boolean isPacked, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
