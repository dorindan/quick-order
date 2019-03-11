package ro.quickorder.backend.resource;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
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
    ReservationRepository reservationRepository;
    @Inject
    CommandRepository commandRepository;
    @Inject
    TableFoodRepository tableFoodRepository;

    @Before
    public void setUp() {

        Command command1 = new Command("Command Name1", "no specifications", false, "ready", null);
        Reservation res1 = new Reservation(null, null, null, null,1, false, null, new ArrayList<>() );

        TableFood table1 = new TableFood(1,5,false,1,true);
        TableFood table2 = new TableFood(2,4,true,1,false);
        TableFood table3 = new TableFood(3,4,false,1,true);
        Command command2 = new Command("Command Name2", "no specifications", false, "ready", null);
        Reservation res2 = new Reservation(null, null, null, null, 1, true, null, new ArrayList<>());

        Command command3 = new Command("Command Name3", "no specifications", true, "ready", null);
        Reservation res3 = new Reservation(null, null, null, null, 1, false, null, new ArrayList<>());

        //save reservation
        Reservation re1 = reservationRepository.save(res1);
        Reservation re2 = reservationRepository.save(res2);
        Reservation re3 = reservationRepository.save(res3);

        // save table
        tableFoodRepository.save(table1);
        tableFoodRepository.save(table3);

        // save table for command
        Command cmd2 = commandRepository.save(command2);
        TableFood tableFood2 = tableFoodRepository.save(table2);
        cmd2.setTable(tableFood2);

        // set commands to reservation
        re1.setCommand(commandRepository.save(command1));
        re2.setCommand(commandRepository.save(cmd2));
        re2.setCommand(commandRepository.save(command3));

        // save reservation
        reservationRepository.save(re1);
        reservationRepository.save(re2);
        reservationRepository.save(re3);

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

        //reservation errors
        testReservationErrors(reservationDtos, tableFoodDtos, tableFoodDtosAfter);

        // table errors
        testTableFoodErrors(reservationDtos, tableFoodDtos, tableFoodDtosAfter);

    }

    private void testReservationErrors(List<ReservationDto> reservationDtos, List<TableFoodDto> tableFoodDtos, List<TableFoodDto> tableFoodDtoListAfter){

        // reservation is taken
        try{
            reservationService.confirmReservation(reservationDtos.get(0),tableFoodDtoListAfter);
            assertEquals(false,true);
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Reservation is already confirmed");
        }

        // reservation is invalid
        reservationDtos.get(0).reservationName = null;
        try{

            reservationService.confirmReservation(reservationDtos.get(0),tableFoodDtos);
            assertEquals(false,true);
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Reservation not found");
        }
    }

    private void testTableFoodErrors(List<ReservationDto> reservationDtoList, List<TableFoodDto> tableFoodDtoList, List<TableFoodDto> tableFoodDtoListAfter){
        // table is taken
        try{
            reservationService.confirmReservation(reservationDtoList.get(1),tableFoodDtoList);
            fail("Table should be taken");
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Table not found");
        }

        // table is invalid
        try{
            reservationService.confirmReservation(reservationDtoList.get(1),tableFoodDtoListAfter);
            assertEquals(false,true);
        }catch (ForbiddenException e){
            assertEquals(e.getMessage(),"TableList can not be null");
        }
    }

}
