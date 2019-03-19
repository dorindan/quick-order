package ro.quickorder.backend.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.CommandRepository;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;
import ro.quickorder.backend.service.TableFoodService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TableFoodServiceTest {

    @Inject
    TableFoodRepository tableFoodRepository;
    @Inject
    TableFoodService tableFoodService;
    @Inject
    ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        TableFood table4 = new TableFood(4, 6, true, 1, true);
        TableFood table5 = new TableFood(5, 5, false, 1, true);

        Timestamp timestampIn = Timestamp.valueOf("2007-09-23 8:10:10.0");
        Timestamp timestampOut = Timestamp.valueOf("2007-09-23 11:10:10.0");
        TableFood table1 = new TableFood(1, 5, false, 1, false);
        TableFood table2 = new TableFood(2, 4, true, 1, true);
        TableFood table3 = new TableFood(3, 4, false, 1, true);
        Reservation res2 = new Reservation(timestampIn, timestampOut, null, null, 1, true, null, new ArrayList<>());

        tableFoodRepository.save(table2);
        tableFoodRepository.save(table3);
        tableFoodRepository.save(table4);
        tableFoodRepository.save(table5);

        //save reservation
        Reservation re2 = reservationRepository.save(res2);

        // save table for command
        TableFood tableFood1 = tableFoodRepository.save(table1);
        List<TableFood> tableFoodList = new ArrayList<>();
        tableFoodList.add(tableFood1);
        res2.setTables(tableFoodList);

        reservationRepository.save(re2);
    }

    @After
    public void tearDown() {
        reservationRepository.deleteAll();
        tableFoodRepository.deleteAll();
    }

    @Test
    public void testgetAllFreeTables() {
        Timestamp timestampIn1 = Timestamp.valueOf("2007-09-23 10:10:10.0");
        Timestamp timestampOut1 = Timestamp.valueOf("2007-09-23 12:10:10.0");
        List<TableFoodDto> rezFree1 = tableFoodService.getAllFree(timestampIn1, timestampOut1);
        assertEquals(4, rezFree1.size());

        Timestamp timestampIn2 = Timestamp.valueOf("2007-09-23 7:10:10.0");
        Timestamp timestampOut2 = Timestamp.valueOf("2007-09-23 10:10:10.0");
        List<TableFoodDto> rezFree2 = tableFoodService.getAllFree(timestampIn2, timestampOut2);
        assertEquals(4, rezFree2.size());

        Timestamp timestampIn3 = Timestamp.valueOf("2007-09-23 9:10:10.0");
        Timestamp timestampOut3 = Timestamp.valueOf("2007-09-23 10:10:10.0");
        List<TableFoodDto> rezFree3 = tableFoodService.getAllFree(timestampIn3, timestampOut3);
        assertEquals(4, rezFree3.size());

        Timestamp timestampIn4 = Timestamp.valueOf("2007-09-23 6:10:10.0");
        Timestamp timestampOut4 = Timestamp.valueOf("2007-09-23 15:10:10.0");
        List<TableFoodDto> rezFree4 = tableFoodService.getAllFree(timestampIn4, timestampOut4);
        assertEquals(4, rezFree4.size());

        Timestamp timestampIn5 = Timestamp.valueOf("2007-09-23 6:10:10.0");
        Timestamp timestampOut5 = Timestamp.valueOf("2007-09-23 7:10:10.0");
        List<TableFoodDto> rezFree5 = tableFoodService.getAllFree(timestampIn5, timestampOut5);
        assertEquals(5, rezFree5.size());

        Timestamp timestampIn6 = Timestamp.valueOf("2007-09-23 14:10:10.0");
        Timestamp timestampOut6 = Timestamp.valueOf("2007-09-23 15:10:10.0");
        List<TableFoodDto> rezFree6 = tableFoodService.getAllFree(timestampIn6, timestampOut6);
        assertEquals(5, rezFree6.size());
    }

    @Test
    public void testgetAllFreeTablesTimestempIsNull() {
        try {
            List<TableFoodDto> rezFree = tableFoodService.getAllFree(null, null);
            fail("");
        } catch (BadRequestException e) {
            assertEquals("Time parameters can not be null", e.getMessage());
        }
    }
}
