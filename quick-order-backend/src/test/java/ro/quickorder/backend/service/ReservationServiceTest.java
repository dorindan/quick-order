package ro.quickorder.backend.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    TableFoodService tableFoodService;
    @Autowired
    TableFoodConverter tableFoodConverter;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CommandRepository commandRepository;
    @Autowired
    TableFoodRepository tableFoodRepository;
    @Autowired
    ReservationConverter reservationConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {

        TableFood table1 = new TableFood(1, 5, false, 1);
        TableFood table2 = new TableFood(2, 4, true, 1);
        TableFood table3 = new TableFood(3, 4, false, 1);

        Timestamp timestampIn1 = Timestamp.valueOf("2007-09-23 11:0:0.0");
        Timestamp timestampOut1 = Timestamp.valueOf("2007-09-23 13:59:0.0");
        Reservation res1 = new Reservation(timestampIn1, timestampOut1, null, null, 1, false, null, new ArrayList<>());
        Timestamp timestampIn2 = Timestamp.valueOf("2007-09-23 9:0:0.0");
        Timestamp timestampOut2 = Timestamp.valueOf("2007-09-23 11:59:0.0");
        Reservation res2 = new Reservation(timestampIn2, timestampOut2, null, null, 1, true, null, new ArrayList<>());
        Timestamp timestampIn3 = Timestamp.valueOf("2007-09-23 9:0:0.0");
        Timestamp timestampOut3 = Timestamp.valueOf("2007-09-23 13:59:0.0");
        Reservation res3 = new Reservation(timestampIn3, timestampOut3, null, null, 1, false, null, new ArrayList<>());
        //save reservation
        res1.setReservationName("res1");
        reservationRepository.save(res1);
        Reservation re2 = reservationRepository.save(res2);
        reservationRepository.save(res3);
        // save table
        tableFoodRepository.save(table2);
        tableFoodRepository.save(table3);
        // save table for command
        TableFood tableFood1 = tableFoodRepository.save(table1);
        List<TableFood> tableFoodList1 = new ArrayList<>();
        tableFoodList1.add(tableFood1);
        re2.setTables(tableFoodList1);
        // save reservation
        reservationRepository.save(re2);
        //create user
        User user1 = new User("hellohello", "$2a$10$bO..vvSzK55NYvsGUdF1s.W9uBCGM8rIHDB/sSGRl2UARiKXrR/7C", "hello@yahoo.com");
        User user2 = new User("hellohelloo", "$2a$10$bO..vvSzK55NYvsGUdF1s.W9uBCGM8rIHDB/sSGRl2UARiKXrR/8C", "helloo@yahoo.com");
        userRepository.save(user1);
        userRepository.save(user2);
        List<Reservation> reservationList = new ArrayList<>();
        res1.setUser(user1);
        res2.setUser(user2);
        res3.setUser(user2);
        reservationRepository.save(res1);
        reservationRepository.save(res2);
        reservationRepository.save(res3);
        reservationList.add(res1);
        //set reservations of user
        user1.setReservations(reservationList);
        userRepository.save(user1);
    }

    @After
    public void tearDown() {
        reservationRepository.deleteAll();
        commandRepository.deleteAll();
        tableFoodRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testGetAllUnconfirmed() {
        List<ReservationDto> reservationDtoList = reservationService.getAllReservationUnconfirmed();
        assertEquals(reservationDtoList.size(), 2);
    }

    /**
     * Confirm Reservation
     */
    @Test
    public void testConfirmReservation() {
        List<ReservationDto> reservationDtos = reservationService.getAllReservationUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+10:10", "23+09+2007+11:10");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        ReservationDto reservationDto = reservationDtos.get(0);
        ConfirmReservationDto confirmReservationDto = reservationConverter.toConfirmReservationDtoFromReservationDto(reservationDto, tableFoodDtos);
        reservationService.confirmReservation(confirmReservationDto);
        List<ReservationDto> reservationDtosAfter = reservationService.getAllReservationUnconfirmed();
        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree("23+09+2007+10:10", "23+09+2007+12:10");
        assertEquals(reservationDtosAfter.size(), 1);
        assertEquals(tableFoodDtosAfter.size(), 0);
    }

    @Test
    public void testConfirmReservationReservationIsInvalid() {
        List<ReservationDto> reservationDtos = reservationService.getAllReservationUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+09:00", "23+09+2007+11:59");
        ReservationDto reservationDto = reservationDtos.get(0);
        ConfirmReservationDto confirmReservationDto = reservationConverter.toConfirmReservationDtoFromReservationDto(reservationDto, tableFoodDtos);
        confirmReservationDto.setReservationName(null);
        try {
            reservationService.confirmReservation(confirmReservationDto);
            fail("Reservation should not have been found");
        } catch (NotFoundException e) {
            assertEquals(e.getMessage(), "Reservation not found");
        }
    }

    @Test
    public void testConfirmReservationTableIsTaken() {
        Timestamp timestampIn = Timestamp.valueOf("2007-09-23 9:0:0.0");
        Timestamp timestampOut = Timestamp.valueOf("2007-09-23 12:59:0.0");
        List<ReservationDto> reservationDtos = reservationService.getAllReservationUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+09:00", "23+09+2007+12:59");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        tableFoodDtos.get(0).setTableNr(100);
        ReservationDto reservationDto = reservationDtos.get(0);
        ConfirmReservationDto confirmReservationDto = reservationConverter.toConfirmReservationDtoFromReservationDto(reservationDto, tableFoodDtos);
        try {
            reservationService.confirmReservation(confirmReservationDto);
            fail("Table should be taken");
        } catch (NotFoundException e) {
            assertEquals(e.getMessage(), "Table not found");
        }
    }

    @Test
    public void testConfirmReservationTableIsInvalid() {
        Timestamp timestampIn = Timestamp.valueOf("2007-09-23 5:0:0.0");
        Timestamp timestampOut = Timestamp.valueOf("2007-09-23 15:59:0.0");
        List<ReservationDto> reservationDtos = reservationService.getAllReservationUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+05:00", "23+09+2007+15:59");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        ReservationDto reservationDto = reservationDtos.get(0);
        ConfirmReservationDto confirmReservationDto = reservationConverter.toConfirmReservationDtoFromReservationDto(reservationDto, tableFoodDtos);
        reservationService.confirmReservation(confirmReservationDto);
        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree("23+09+2007+05:00", "23+09+2007+15:59");
        try {
            ReservationDto reservationDto2 = reservationDtos.get(1);
            ConfirmReservationDto confirmReservationDto2 = reservationConverter.toConfirmReservationDtoFromReservationDto(reservationDto2, tableFoodDtosAfter);
            reservationService.confirmReservation(confirmReservationDto2);
            fail("The list of tables should be invalid!");
        } catch (ForbiddenException e) {
            assertEquals(e.getMessage(), "The list of tables can not be empty");
        }
    }

    @Test
    public void testAddReservation() {
        try {
            ReservationDto reservationDto = new ReservationDto.Builder().withnumberOfPersons(12).withCheckInTime(new Timestamp(12)).build();
            reservationService.addReservation(reservationDto);
        } catch (ForbiddenException e) {
            assertEquals(e.getMessage(), "CheckInTime must be greater than the current date");
        }
        try {
            ReservationDto reservationDto = new ReservationDto.Builder().withCheckInTime(new Timestamp(System.currentTimeMillis() + 10000)).withnumberOfPersons(100).build();
            reservationService.addReservation(reservationDto);
        } catch (ForbiddenException e) {
            assertEquals(e.getMessage(), "Number of persons for a reservation must be between 1 and 99");
        }
    }

    @Test
    public void testReservationsForTable() {
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        TableFoodDto tableFoodDto1 = tableFoodConverter.toTableFoodDto(tableFoods.get(0));
        List<ReservationDto> reservations1 = reservationService.getReservationsForTableByTableNumber(tableFoodDto1.getTableNr());
        assertEquals(1, reservations1.size());
        TableFoodDto tableFoodDto2 = tableFoodConverter.toTableFoodDto(tableFoods.get(1));
        List<ReservationDto> reservations2 = reservationService.getReservationsForTableByTableNumber(tableFoodDto2.getTableNr());
        assertEquals(0, reservations2.size());
    }

    @Test
    public void testReservationsForTableTableNotFound() {
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        TableFoodDto tableFoodDto1 = tableFoodConverter.toTableFoodDto(tableFoods.get(1));
        tableFoodDto1.setTableNr(213);
        try {
            reservationService.getReservationsForTableByTableNumber(tableFoodDto1.getTableNr());
            fail("The reservation should not have been found");
        } catch (NotFoundException e) {
            assertEquals("Table not found!", e.getMessage());
        }
    }

    @Test
    public void testAddReservationConfirmed() {
        long twoHoursInMilliseconds = 7200000;
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis() + twoHoursInMilliseconds);
        ReservationDto reservationDto = new ReservationDto.Builder().withnumberOfPersons(12).withCheckInTime(currentTimestamp).build();
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        reservationDto.setTableFoodDtos(tableFoods.stream()
                .map(tableFood -> tableFoodConverter.toTableFoodDto(tableFood)).collect(Collectors.toList()));
        List<Reservation> reservationsBefor = reservationRepository.findAll();
        assertEquals(reservationsBefor.size(), 3);
        Long confirmed = reservationsBefor.stream().filter(reservation -> reservation.isConfirmed()).count();
        reservationService.addReservation(reservationDto);
        List<Reservation> reservationsAfter = reservationRepository.findAll();
        Long confirmedAfter = reservationsAfter.stream().filter(reservation -> reservation.isConfirmed()).count();
        assertEquals(reservationsAfter.size(), 4);
        assertEquals((Long) (confirmed + 1), confirmedAfter);
    }

    @Test
    public void testAddReservationConfirmedNotEnoughSeats() {
        long twoHoursInMilliseconds = 7200000;
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis() + twoHoursInMilliseconds);
        ReservationDto reservationDto = new ReservationDto.Builder().withnumberOfPersons(98).withCheckInTime(currentTimestamp).build();
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        List<TableFoodDto> tableFoodDtoList = new ArrayList<>();
        tableFoodDtoList.add(tableFoodConverter.toTableFoodDto(tableFoods.get(0)));
        reservationDto.setTableFoodDtos(tableFoodDtoList);
        try {
            reservationService.addReservation(reservationDto);
            fail("Table should not have enough seats for all persons!");
        } catch (BadRequestException e) {
            assertEquals("Not enough seats for all persons!", e.getMessage());
        }
    }

    @Test
    public void testReservationOfActualUser() {
        userService.login(new UserDto("hellohello",
                "hellohello", "hello@yahoo.com"));
        List<ReservationDto> reservationDtos = reservationService.reservationOfActualUser();
        assertEquals(1, reservationDtos.size());
    }

    @Test
    public void testRemoveReservation() {
        userService.login(new UserDto("hellohello",
                "hellohello", "hello@yahoo.com"));
        reservationService.removeReservation("res1");
        List<ReservationDto> reservationDtos = reservationService.reservationOfActualUser();
        assertEquals(0, reservationDtos.size());
    }
}
