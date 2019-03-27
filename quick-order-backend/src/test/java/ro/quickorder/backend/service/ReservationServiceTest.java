package ro.quickorder.backend.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.CommandRepository;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;
import ro.quickorder.backend.service.ReservationService;
import ro.quickorder.backend.service.TableFoodService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {

    @Inject
    ReservationService reservationService;
    @Inject
    TableFoodService tableFoodService;
    @Inject
    TableFoodConverter tableFoodConverter;

    @Inject
    ReservationRepository reservationRepository;
    @Inject
    CommandRepository commandRepository;
    @Inject
    TableFoodRepository tableFoodRepository;

    @Before
    public void setUp() {

        Reservation res1 = new Reservation(null, null, null, null,1, false, null, new ArrayList<>() );

        TableFood table1 = new TableFood(1,5,false,1,true);
        TableFood table2 = new TableFood(2,4,true,1,false);
        TableFood table3 = new TableFood(3,4,false,1,true);
        Reservation res2 = new Reservation(null, null, null, null, 1, true, null, new ArrayList<>());

        Reservation res3 = new Reservation(null, null, null, null, 1, false, null, new ArrayList<>());

        //save reservation
        Reservation reservation1 = reservationRepository.save(res1);
        Reservation reservation2 = reservationRepository.save(res2);
        Reservation reservation3 = reservationRepository.save(res3);

        // save table
        TableFood tableFood1 = tableFoodRepository.save(table1);
        TableFood tableFood2 = tableFoodRepository.save(table2);
        TableFood tableFood3 = tableFoodRepository.save(table3);


        List<TableFood> tableFoods = new ArrayList<>();
        tableFoods.add(tableFood1);
        tableFoods.add(tableFood2);

        reservation1.setTables(tableFoods);

        tableFoods = new ArrayList<>();
        tableFoods.add(tableFood2);
        tableFoods.add(tableFood3);

        // save table
        reservation2.setTables(tableFoods);

        // save reservation
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);

    }

    @After
    public void tearDown(){
        reservationRepository.deleteAll();
        commandRepository.deleteAll();
        tableFoodRepository.deleteAll();
    }



    @Test
    public void testGetAllUnconfirmed(){
        List<ReservationDto> reservationDtoList = reservationService.getAllUnconfirmed();

        assertEquals(reservationDtoList.size(),2);
    }

    // Confirm Reservation --------------------
    @Test
    public void testConfirmReservation() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree();

        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);

        reservationService.confirmReservation(reservationDtos.get(0), tableFoodDtos);

        List<ReservationDto> reservationDtosAfter = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree();

        assertEquals(reservationDtosAfter.size(), 1);
        assertEquals(tableFoodDtosAfter.size(), 0);
    }

    @Test
    public void testConfirmReservationReservationIsTaken() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree();

        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);

        reservationService.confirmReservation(reservationDtos.get(0), tableFoodDtos);

        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree();

        try{
            reservationService.confirmReservation(reservationDtos.get(0),tableFoodDtosAfter);
            fail("Reservation should be taken");
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Reservation is already confirmed");
        }
    }

    @Test
    public void testConfirmReservationReservationIsInvalid() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree();

        reservationDtos.get(0).setReservationName(null);
        try{

            reservationService.confirmReservation(reservationDtos.get(0),tableFoodDtos);
            fail("Reservation should not have been found");
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Reservation not found");
        }
    }

    @Test
    public void testConfirmReservationTableIsTaken() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree();

        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);

        reservationService.confirmReservation(reservationDtos.get(0), tableFoodDtos);

        try{
            reservationService.confirmReservation(reservationDtos.get(1),tableFoodDtos);
            fail("Table should be taken");
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Table not found");
        }
    }

    @Test
    public void testConfirmReservationTableIsInvalid() {
        List<ReservationDto> reservationDtos = reservationService.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtos = tableFoodService.getAllFree();

        assertEquals(reservationDtos.size(), 2);
        assertEquals(tableFoodDtos.size(), 2);

        reservationService.confirmReservation(reservationDtos.get(0), tableFoodDtos);

        List<TableFoodDto> tableFoodDtosAfter = tableFoodService.getAllFree();
        try{
            reservationService.confirmReservation(reservationDtos.get(1),tableFoodDtosAfter);
            fail("The list of tables should be invalid!");
        }catch (ForbiddenException e){
            assertEquals(e.getMessage(),"TableList can not be null");
        }
    }
    // ------------

    @Test
    public void testAddReservation(){
        try{
            ReservationDto reservationDto = new ReservationDto.Builder().withnumberOfPersons(12).withCheckInTime(new Timestamp(12)).build();
            reservationService.addReservation(reservationDto);

        }catch(ForbiddenException e){
            assertEquals(e.getMessage(),"CheckInTime must be greater than the current date");
        }

        try{
            ReservationDto reservationDto = new ReservationDto.Builder().withCheckInTime(new Timestamp(System.currentTimeMillis() + 10000)).withnumberOfPersons(100).build();
            reservationService.addReservation(reservationDto);

        }catch(ForbiddenException e){
            assertEquals(e.getMessage(),"Number of persons for a reservation must be between 1 and 99");
        }

    }

    @Test
    public void testReservationsForTable(){
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        TableFoodDto tableFoodDto1 = tableFoodConverter.toTableFoodDto(tableFoods.get(1));
        List<ReservationDto> reservations1 = reservationService.reservationsForTable(tableFoodDto1.getTableNr());
        assertEquals(2, reservations1.size());

        TableFoodDto tableFoodDto2 = tableFoodConverter.toTableFoodDto(tableFoods.get(0));
        List<ReservationDto> reservations2 = reservationService.reservationsForTable(tableFoodDto2.getTableNr());
        assertEquals(1, reservations2.size());
    }

    @Test
    public void testReservationsForTableTableNotFound(){
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        TableFoodDto tableFoodDto1 = tableFoodConverter.toTableFoodDto(tableFoods.get(1));
        tableFoodDto1.setTableNr(213);
        try{
            List<ReservationDto> reservations1 = reservationService.reservationsForTable(tableFoodDto1.getTableNr());
            fail("The reservation should not have been found");
        }catch (NotFoundException e){
            assertEquals("Table not found!", e.getMessage());
        }
    }

}
