package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link CommandConverter}
 *
 * @author R. Lupoaie
 */
public class CommandConverterTest {
    private CommandConverter commandConverter = new CommandConverter();

    @Test
    public void testConvertCommandToDto() {
        Command command = new Command("command1", "specification1", false, CommandStatus.DONE, null);
        CommandDto commandDto = commandConverter.toCommandDto(command);
        assertEquals(command.getCommandName(), commandDto.getCommandName());
        assertEquals(command.getSpecification(), commandDto.getSpecification());
        assertEquals(command.getStatus(), commandDto.getStatus());
        assertEquals(command.isPacked(), commandDto.isPacked());
    }

    @Test
    public void testConvertCommandToDtoWhenCommandIsNull() {
        CommandDto commandDto = commandConverter.toCommandDto(null);
        assertNull(commandDto);
    }

    @Test
    public void testConvertDtoToCommand() {
        CommandDto commandDto = new CommandDto("command1", "specification1", false, CommandStatus.DONE);
        Command command = commandConverter.toCommand(commandDto);
        assertEquals(commandDto.getCommandName(), command.getCommandName());
        assertEquals(commandDto.getSpecification(), command.getSpecification());
        assertEquals(commandDto.getStatus(), command.getStatus());
        assertEquals(commandDto.isPacked(), command.isPacked());
        assertNull(command.getMenuItems());
        assertNull(command.getBill());
        assertNull(command.getUsers());
        assertNull(command.getTable());
        assertNull(command.getReservations());
    }

    @Test
    public void testConvertDtoToCommandWhenDtoIsNull() {
        Command command = commandConverter.toCommand(null);
        assertNull(command);
    }
}