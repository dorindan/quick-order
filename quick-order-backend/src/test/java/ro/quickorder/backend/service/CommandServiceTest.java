package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.converter.UserConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;
import ro.quickorder.backend.repository.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author R. Lupoaie
 */
@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommandServiceTest {
    @Autowired
    TableFoodRepository tableFoodRepository;
    @Autowired
    MenuItemCommandRepository menuItemCommandRepository;
    @Autowired
    UserConverter userConverter;
    @Autowired
    UserService userService;
    @Autowired
    private CommandService commandService;
    @Autowired
    private MenuItemConverter menuItemConverter;
    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Before
    public void setUp() {
        TableFood table = new TableFood(5, 5, false, 1);
        TableFood tableFood = tableFoodRepository.save(table);


        MenuItem menuItem1 = new MenuItem("Name1", "Original description!", 12, 20, null);
        MenuItem menuItem2 = new MenuItem("Name2", "Original description!", 20, 30, null);
        MenuItem menuItem3 = new MenuItem("Name3", "Original description!", 25, 40, null);

        menuItemRepository.save(menuItem1);
        menuItemRepository.save(menuItem2);
        menuItemRepository.save(menuItem3);

        User user1 = new User("hellohello", "$2a$10$bO..vvSzK55NYvsGUdF1s.W9uBCGM8rIHDB/sSGRl2UARiKXrR/7C", "hello@yahoo.com");
        User user = userRepository.save(user1);

        Command command = new Command("Test command", "The test need to work", false, CommandStatus.ACTIVE, tableFood);
        command.setUser(user);
        commandRepository.save(command);

        List<Command> commands = new ArrayList<>();
        commands.add(command);
        user.setCommands(commands);
        userRepository.save(user);
    }

    @After
    public void tearDown() {
        menuItemCommandRepository.deleteAll();
        commandRepository.deleteAll();
        tableFoodRepository.deleteAll();
        userRepository.deleteAll();
        menuItemRepository.deleteAll();
    }

    @Test
    public void testAddCommand() {
        CommandDto commandDto = new CommandDto();
        User user = userRepository.findByUsername("hellohello");
        commandDto.setUserDto(userConverter.toUserDto(user));
        commandDto.setSpecification("Putina maioneza");
        commandDto.setPacked(true);
        MenuItem menuItem = menuItemRepository.findByName("Name1");
        MenuItemCommandDto menuItemCommandDto = new MenuItemCommandDto();
        menuItemCommandDto.setAmount(7);
        menuItemCommandDto.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItem));
        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        menuItemCommandDtos.add(menuItemCommandDto);
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);
        commandService.addCommand(commandDto);
        List<Command> commands = commandRepository.findAll();

        assertEquals(2, commands.size());
        assertEquals("Putina maioneza", commands.get(1).getSpecification());
        assertTrue(commands.get(1).isPacked());
        assertEquals(1, commands.get(1).getMenuItemCommands().size());
        assertEquals("Name1", commands.get(1).getMenuItemCommands().get(0).getMenuItem().getName());
        assertEquals(new Integer(7), commands.get(1).getMenuItemCommands().get(0).getAmount());
    }

    @Test(expected = NotFoundException.class)
    public void testAddCommandWithUserNotFound() {
        CommandDto commandDto = new CommandDto();
        commandDto.setUserDto(new UserDto("nonExisting", "password", "email"));
        commandService.addCommand(commandDto);
    }

    @Test(expected = NotFoundException.class)
    public void testAddCommandWithMenuItemNotFound() {
        CommandDto commandDto = new CommandDto();
        User user = userRepository.findByUsername("hellohello");
        commandDto.setUserDto(userConverter.toUserDto(user));
        MenuItem menuItem = menuItemRepository.findByName("Name1");
        MenuItemCommandDto menuItemCommandDto = new MenuItemCommandDto();
        menuItemCommandDto.setAmount(7);
        menuItemCommandDto.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItem));
        menuItemCommandDto.getMenuItemDto().setName("UnExisting Item");
        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        menuItemCommandDtos.add(menuItemCommandDto);
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);
        commandService.addCommand(commandDto);
    }

    @Test
    public void testUpdateCommand() {
        CommandDto commandDto = new CommandDto("Test command", "Specification changed", true, CommandStatus.ACTIVE);

        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertEquals(3, menuItems.size());

        MenuItemCommandDto menuItemCommandDto1 = new MenuItemCommandDto();
        menuItemCommandDto1.setAmount(2);
        menuItemCommandDto1.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItems.get(0)));

        MenuItemCommandDto menuItemCommandDto2 = new MenuItemCommandDto();
        menuItemCommandDto2.setAmount(3);
        menuItemCommandDto2.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItems.get(1)));

        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        menuItemCommandDtos.add(menuItemCommandDto1);
        menuItemCommandDtos.add(menuItemCommandDto2);
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);

        commandService.updateCommand(commandDto);

        Command commandAfter = commandRepository.findByCommandNameWithItems(commandDto.getCommandName());
        assertNotNull(commandAfter);
        assertEquals("Test command", commandAfter.getCommandName());
        assertEquals("Specification changed", commandAfter.getSpecification());
        assertEquals(2, commandAfter.getMenuItemCommands().size());
        assertEquals(new Integer(2), commandAfter.getMenuItemCommands().get(0).getAmount());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateCommandWithCommandNotFound() {
        commandService.updateCommand(new CommandDto());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateCommandWhenMenuItemNotFoundNewMenuItem() {
        CommandDto commandDto = new CommandDto("Test command", "Specification changed", true, CommandStatus.ACTIVE);

        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertEquals(3, menuItems.size());

        MenuItemCommandDto menuItemCommandDto1 = new MenuItemCommandDto();
        menuItemCommandDto1.setAmount(2);
        menuItemCommandDto1.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItems.get(0)));

        MenuItemCommandDto menuItemCommandDto2 = new MenuItemCommandDto();
        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        menuItemCommandDtos.add(menuItemCommandDto1);
        menuItemCommandDtos.add(menuItemCommandDto2);
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);

        commandService.updateCommand(commandDto);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateCommandWhenMenuItemNotFoundWrongMenuItemName() {
        CommandDto commandDto = new CommandDto("Test command", "Specification changed", true, CommandStatus.ACTIVE);

        List<MenuItem> menuItems = menuItemRepository.findAll();
        assertEquals(3, menuItems.size());

        MenuItemCommandDto menuItemCommandDto1 = new MenuItemCommandDto();
        menuItemCommandDto1.setAmount(2);
        menuItemCommandDto1.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItems.get(0)));

        MenuItemCommandDto menuItemCommandDto2 = new MenuItemCommandDto();
        List<MenuItemCommandDto> menuItemCommandDtos = new ArrayList<>();
        menuItemCommandDtos.add(menuItemCommandDto1);
        menuItemCommandDtos.add(menuItemCommandDto2);
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);

        menuItemCommandDto2.setAmount(2);
        menuItemCommandDto2.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItems.get(0)));
        menuItemCommandDto2.getMenuItemDto().setName("Wrong name");
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);

        commandService.updateCommand(commandDto);
    }

    @Test
    public void testReservationOfActualUser() {
        userService.login(new UserDto("hellohello",
                "hellohello", "hello@yahoo.com"));
        List<CommandDto> commandDtos = commandService.getCommandsOfUser();
        assertEquals(1, commandDtos.size());
    }

    @Test
    public void testRemoveReservation() {
        commandService.removeCommand("test_command2");
        Command command = commandRepository.findByCommandName("test_command2");
        assertEquals(null, command);
    }

    @Test
    public void testGetUnconfirmedCommands() {
        List<CommandDto> commandDtos = commandService.getCommandsWithStatus(CommandStatus.DONE);
        assertEquals(1, commandDtos.size());
    }

    @Test
    public void testConfirmCommand() {
        commandService.confirmCommand(new CommandDto("Test command", "", false, CommandStatus.DONE));
        List<CommandDto> commandDtos = commandService.getCommandsWithStatus(CommandStatus.DONE);
        assertEquals(0, commandDtos.size());
    }
}
