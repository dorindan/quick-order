package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.CommandDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.CommandRepository;
import ro.quickorder.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommandServiceTest {
    @Autowired
    CommandService commandService;
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Before
    public void setUp() {
        Command command = new Command("test_command", "", false, "unconfirmed", null);
        commandRepository.save(command);
        Command command2 = new Command("test_command2", "", false, "confirmed", null);
        commandRepository.save(command2);
        List<User> users = new ArrayList<>();
        User user = new User("hellohello", "$2a$10$bO..vvSzK55NYvsGUdF1s.W9uBCGM8rIHDB/sSGRl2UARiKXrR/7C", "hello@yahoo.com");
        userRepository.save(user);
        users.add(user);
        command.setUsers(users);
        command.setMenuItems(null);
        command.setReservations(null);
        commandRepository.save(command);
        List<Command> commands = new ArrayList<>();
        commands.add(command);
        user.setCommands(commands);
        userRepository.save(user);
        System.out.println(commandRepository.findByCommandName("test_command").getUsers().size());
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
        commandRepository.deleteAll();
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
        List<CommandDto> commandDtos = commandService.getCommandsWithStatus("unconfirmed");
        assertEquals(1, commandDtos.size());
    }

    @Test
    public void testConfirmCommand() {
        commandService.confirmCommand(new CommandDto("test_command", "", false, ""));
        List<CommandDto> commandDtos = commandService.getCommandsWithStatus("unconfirmed");
        assertEquals(0, commandDtos.size());
    }
}
