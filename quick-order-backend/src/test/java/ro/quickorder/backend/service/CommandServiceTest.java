package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.*;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.dto.MenuItemCommandDto;
import ro.quickorder.backend.model.enumeration.CommandStatus;
import ro.quickorder.backend.repository.*;

import javax.inject.Inject;

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


    @Inject
    private CommandService commandService;
    @Inject
    private MenuItemConverter menuItemConverter;
    @Inject
    private CommandRepository commandRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private MenuItemRepository menuItemRepository;
    @Inject
    TableFoodRepository tableFoodRepository;
    @Inject
    MenuItemCommandRepository menuItemCommandRepository;

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
        List<User> users = new ArrayList<>();
        users.add(user);
        command.setUsers(users);
        commandRepository.save(command);
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
    public void testUserActiveCommand() {
        CommandDto commandDto = commandService.getUserActiveCommand("hellohello");
        assertNotNull(commandDto);
        assertEquals("Test command", commandDto.getCommandName());
        assertEquals("The test need to work", commandDto.getSpecification());
    }

    @Test
    public void testUserActiveCommandWithUserNotFound() {
        try {
            commandService.getUserActiveCommand("admin");
            fail("User should not have been found");
        } catch (NotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }
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

        CommandDto commandDtoAfter = commandService.getUserActiveCommand("hellohello");
        assertNotNull(commandDtoAfter);
        assertEquals("Test command", commandDtoAfter.getCommandName());
        assertEquals("Specification changed", commandDtoAfter.getSpecification());
        assertEquals(2, commandDtoAfter.getMenuItemCommandDtos().size());
        assertEquals(new Integer(2), commandDtoAfter.getMenuItemCommandDtos().get(0).getAmount());
    }

    @Test
    public void testUpdateCommandWithCommandNotFound() {
        try {
            commandService.updateCommand(new CommandDto());
            fail("Command should not have been found");
        } catch (NotFoundException e) {
            assertEquals("Command not found", e.getMessage());
        }
    }

    @Test
    public void testUpdateCommandWhenMenuItemNotFound() {
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

        try {
            commandService.updateCommand(commandDto);
            fail("MenuItem should not have been found");
        } catch (NotFoundException e) {
            assertEquals("MenuItem not found", e.getMessage());
        }

        menuItemCommandDto2.setAmount(2);
        menuItemCommandDto2.setMenuItemDto(menuItemConverter.toMenuItemDto(menuItems.get(0)));
        menuItemCommandDto2.getMenuItemDto().setName("Wrong name");
        commandDto.setMenuItemCommandDtos(menuItemCommandDtos);
        try {
            commandService.updateCommand(commandDto);
            fail("MenuItem should not have been found");
        } catch (NotFoundException e) {
            assertEquals("MenuItem not found", e.getMessage());
        }
    }
}
