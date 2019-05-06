package ro.quickorder.backend.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ConfirmReservationDto;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.CommandRepository;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    }

    @After
    public void tearDown() {
        reservationRepository.deleteAll();
        commandRepository.deleteAll();
        tableFoodRepository.deleteAll();
    }

    @Test
    public void testGetAllUnconfirmed() {
        List<ReservationDto> reservationDtoList = reservationService.getAllUnconfirmed();
        assertEquals(reservationDtoList.size(), 2);
    }

    /**
     * Confirm Reservation
     */
    @Test
    public void testConfirmReservation() {
        ;
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+10:10", "23+09+2007+11:10");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        ConfirmReservationDto confirmReservationDto = new ConfirmReservationDto(reservationDtos.get(0).getCheckInTime(),
                reservationDtos.get(0).getCheckOutTime(), reservationDtos.get(0).getStatus(), reservationDtos.get(0).isConfirmed(),
                reservationDtos.get(0).getNumberOfPersons(), reservationDtos.get(0).getReservationName(), tableFoodDtos);
        reservationService.confirmReservation(confirmReservationDto);
        List<ReservationDto> reservationDtosAfter = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree("23+09+2007+10:10", "23+09+2007+12:10");
        assertEquals(reservationDtosAfter.size(), 1);
        assertEquals(tableFoodDtosAfter.size(), 0);
    }

    @Test
    public void testConfirmReservationReservationIsTaken() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+08:00", "23+09+2007+10:59");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        ConfirmReservationDto confirmReservationDto = new ConfirmReservationDto(reservationDtos.get(0).getCheckInTime(),
                reservationDtos.get(0).getCheckOutTime(), reservationDtos.get(0).getStatus(), reservationDtos.get(0).isConfirmed(),
                reservationDtos.get(0).getNumberOfPersons(), reservationDtos.get(0).getReservationName(), tableFoodDtos);
        reservationService.confirmReservation(confirmReservationDto);
        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree("23+09+2007+08:00", "23+09+2007+10:59");
        try {
            reservationService.confirmReservation(confirmReservationDto);
            fail("Reservation should be taken");
        } catch (NotFoundException e) {
            assertEquals(e.getMessage(), "Reservation is already confirmed");
        }
    }

    @Test
    public void testConfirmReservationReservationIsInvalid() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+09:00", "23+09+2007+11:59");
        ConfirmReservationDto confirmReservationDto = new ConfirmReservationDto(reservationDtos.get(0).getCheckInTime(),
                reservationDtos.get(0).getCheckOutTime(), reservationDtos.get(0).getStatus(), reservationDtos.get(0).isConfirmed(),
                reservationDtos.get(0).getNumberOfPersons(), reservationDtos.get(0).getReservationName(), tableFoodDtos);
        reservationDtos.get(0).setReservationName(null);
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
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+09:00", "23+09+2007+12:59");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        tableFoodDtos.get(0).setTableNr(100);
        ConfirmReservationDto confirmReservationDto = new ConfirmReservationDto(reservationDtos.get(0).getCheckInTime(),
                reservationDtos.get(0).getCheckOutTime(), reservationDtos.get(0).getStatus(), reservationDtos.get(0).isConfirmed(),
                reservationDtos.get(0).getNumberOfPersons(), reservationDtos.get(0).getReservationName(), tableFoodDtos);
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
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree("23+09+2007+05:00", "23+09+2007+15:59");
        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);
        ConfirmReservationDto confirmReservationDto = new ConfirmReservationDto(reservationDtos.get(0).getCheckInTime(),
                reservationDtos.get(0).getCheckOutTime(), reservationDtos.get(0).getStatus(), reservationDtos.get(0).isConfirmed(),
                reservationDtos.get(0).getNumberOfPersons(), reservationDtos.get(0).getReservationName(), tableFoodDtos);
        reservationService.confirmReservation(confirmReservationDto);
        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree("23+09+2007+05:00", "23+09+2007+15:59");
        try {
            ConfirmReservationDto confirmReservationDto2 = new ConfirmReservationDto(reservationDtos.get(1).getCheckInTime(),
                    reservationDtos.get(1).getCheckOutTime(), reservationDtos.get(1).getStatus(), reservationDtos.get(1).isConfirmed(),
                    reservationDtos.get(1).getNumberOfPersons(), reservationDtos.get(1).getReservationName(), tableFoodDtos);
            reservationService.confirmReservation(confirmReservationDto2);
            fail("The list of tables should be invalid!");
        } catch (ForbiddenException e) {
            assertEquals(e.getMessage(), "TableList can not be null");
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
        TableFoodDto tableFoodDto1 = tableFoodConverter.toTableFoodDto(tableFoods.get(2));
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

}
