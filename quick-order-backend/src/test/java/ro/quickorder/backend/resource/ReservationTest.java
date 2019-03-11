package ro.quickorder.backend.resource;


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

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationTest {

    @Inject
    ReservationResource reservationResource;
    @Inject
    TableFoodResource tableFoodResource;

    @Inject
    ReservationRepository reservationRepository;
    @Inject
    CommandRepository commandRepository;
    @Inject
    TableFoodRepository tableFoodRepository;

    @Before
    public void setUp() {

        Command command1 = new Command("Command Name1", "no specifications", false, "ready", null);
        Reservation res1 = new Reservation(null, null, null, null, false, null);

        TableFood table1 = new TableFood(1,5,false,1,true);
        TableFood table2 = new TableFood(2,4,true,1,false);
        TableFood table3 = new TableFood(3,4,false,1,true);
        Command command2 = new Command("Command Name2", "no specifications", false, "ready", null);
        Reservation res2 = new Reservation(null, null, null, null, true, null);

        Command command3 = new Command("Command Name3", "no specifications", true, "ready", null);
        Reservation res3 = new Reservation(null, null, null, null, false, null);

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


    @Test
    public void testGetAllUnconfirmed(){
        List<ReservationDto> reservationDtoList = reservationResource.getAllUnconfirmed();

        assertEquals(reservationDtoList.size(),2);
    }

    @Test
    public void testConfirmReservation(){
        List<ReservationDto> reservationDtoList = reservationResource.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtoList = tableFoodResource.getAllFree();

        assertEquals(reservationDtoList.size(),2);
        assertEquals(tableFoodDtoList.size(),2);

        reservationResource.confirmReservation(reservationDtoList.get(0),tableFoodDtoList);

        List<ReservationDto> reservationDtoListAfter = reservationResource.getAllUnconfirmed();
        List<TableFoodDto> tableFoodDtoListAfter = tableFoodResource.getAllFree();

        assertEquals(reservationDtoListAfter.size(),1);
        assertEquals(tableFoodDtoListAfter.size(),0);

        // reservation is taken
        try{

            reservationResource.confirmReservation(reservationDtoList.get(0),tableFoodDtoListAfter);
            assertEquals(false,true);
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Reservation is already confirmed");
        }

        // table is taken
        try{

            reservationResource.confirmReservation(reservationDtoList.get(1),tableFoodDtoList);
            assertEquals(false,true);
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Table is already taken!");
        }


        // reservation is invalid
        reservationDtoList.get(0).reservationName = null;
        try{

            reservationResource.confirmReservation(reservationDtoList.get(0),tableFoodDtoList);
            assertEquals(false,true);
        }catch (NotFoundException e){
            assertEquals(e.getMessage(),"Reservation not found");
        }

        // table is invalid
        try{

            reservationResource.confirmReservation(reservationDtoList.get(1),tableFoodDtoListAfter);
            assertEquals(false,true);
        }catch (ForbiddenException e){
            assertEquals(e.getMessage(),"TableList can not be null");
        }
    }

}
