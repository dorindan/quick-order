package ro.quickorder.backend.service;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotAcceptableException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TableFoodServiceTest {
    @Autowired
    TableFoodRepository tableFoodRepository;
    @Autowired
    TableFoodService tableFoodService;
    @Autowired
    ReservationRepository reservationRepository;

    @Before
    public void setUp() {
        TableFood table4 = new TableFood(4, 6, true, 1);
        TableFood table5 = new TableFood(5, 5, false, 1);

        Timestamp timestampIn1 = Timestamp.valueOf("2007-09-23 8:10:10.0");
        Timestamp timestampOut1 = Timestamp.valueOf("2007-09-23 11:10:10.0");
        TableFood table1 = new TableFood(1, 5, false, 1);
        TableFood table2 = new TableFood(2, 4, true, 1);
        TableFood table3 = new TableFood(3, 4, false, 1);
        Reservation res2 = new Reservation(timestampIn1, timestampOut1, null, null, 1, true, null, new ArrayList<>());

        Timestamp timestampIn2 = Timestamp.valueOf("2007-09-23 9:10:10.0");
        Timestamp timestampOut2 = Timestamp.valueOf("2007-09-23 12:10:10.0");
        Reservation res3 = new Reservation(timestampIn2, timestampOut2, null, null, 1,
                true, null, new ArrayList<>());
        tableFoodRepository.save(table5);
        // save table for command
        TableFood tableFood1 = tableFoodRepository.save(table1);
        TableFood tableFood4 = tableFoodRepository.save(table4);
        List<TableFood> tableFoodList1 = new ArrayList<>();
        tableFoodList1.add(tableFood1);
        tableFoodList1.add(tableFood4);
        res2.setTables(tableFoodList1);
        TableFood tableFood2 = tableFoodRepository.save(table2);
        TableFood tableFood3 = tableFoodRepository.save(table3);
        List<TableFood> tableFoodList2 = new ArrayList<>();
        tableFoodList2.add(tableFood2);
        tableFoodList2.add(tableFood3);
        res3.setTables(tableFoodList2);
        //save reservation
        Reservation re2 = reservationRepository.save(res2);
        Reservation re3 = reservationRepository.save(res3);
        reservationRepository.save(re2);
        reservationRepository.save(re3);
    }

    @After
    public void tearDown() {
        reservationRepository.deleteAll();
        tableFoodRepository.deleteAll();
    }

    @Test
    public void testGetAllFreeTables() {
        List<TableFoodDto> rezFree1 = tableFoodService.getAllFree("23+09+2007+10:10", "23+09+2007+12:10");
        assertEquals(1, rezFree1.size());
        List<TableFoodDto> rezFree2 = tableFoodService.getAllFree("23+09+2007+07:10", "23+09+2007+09:10");
        assertEquals(3, rezFree2.size());
        List<TableFoodDto> rezFree3 = tableFoodService.getAllFree("23+09+2007+11:10", "23+09+2007+13:10");
        assertEquals(1, rezFree3.size());
        List<TableFoodDto> rezFree4 = tableFoodService.getAllFree("23+09+2007+06:10", "23+09+2007+15:10");
        assertEquals(1, rezFree4.size());
        List<TableFoodDto> rezFree5 = tableFoodService.getAllFree("23+09+2007+06:10", "23+09+2007+07:10");
        assertEquals(5, rezFree5.size());
        List<TableFoodDto> rezFree6 = tableFoodService.getAllFree("23+09+2007+14:10", "23+09+2007+15:10");
        assertEquals(5, rezFree6.size());
    }

    @Test(expected = NotAcceptableException.class)
    public void testGetAllFreeTablesTimestempIsNull() {
        tableFoodService.getAllFree(null, null);
        fail("Time parameter is null, it should throw an error");
    }

    @Test
    public void testAddTable() {
        TableFoodDto tableFoodDto = new TableFoodDto(47, 12, true, 1);

        List<TableFood> tableFoods = tableFoodRepository.findAll();
        assertEquals(5, tableFoods.size());

        tableFoodService.addTable(tableFoodDto);

        List<TableFood> newTableFoods = tableFoodRepository.findAll();
        assertEquals(6, newTableFoods.size());
    }

    @Test(expected = BadRequestException.class)
    public void testAddTableWhenTableAlreadyExists() {
        TableFoodDto tableFoodDto = new TableFoodDto(1, 12, true, 1);
        tableFoodService.addTable(tableFoodDto);
        fail("Table already exists, BadRequestException should be thrown");

    }

    @Test
    public void testUpdateTable() {
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        assertEquals(5, tableFoods.size());

        TableFoodDto tableFoodDto = new TableFoodDto(tableFoods.get(0).getTableNr(), 12, true, 1);

        tableFoodService.updateTable(tableFoodDto);

        TableFood newTableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
        assertEquals(1, newTableFood.getFloor());
        assertEquals(12, newTableFood.getSeats());
        assertTrue(newTableFood.isWindowView());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateTableWhenTableDoseNotExists() {
        TableFoodDto tableFoodDto = new TableFoodDto(47, 12, true, 1);
        tableFoodService.updateTable(tableFoodDto);
        fail("Table dose not exists, NotFoundException should be thrown");
    }

    @Test
    public void testRemoveTable() {
        List<TableFood> tableFoods = tableFoodRepository.findAll();
        assertEquals(5, tableFoods.size());

        tableFoodService.removeTable(tableFoods.get(0).getTableNr());

        List<TableFood> newTableFoods = tableFoodRepository.findAll();
        assertEquals(4, newTableFoods.size());
    }

    @Test(expected = NotFoundException.class)
    public void testRemoveTableWhenTableDoseNotExists() {
        tableFoodService.removeTable(47);
        fail("Table dose not exists, NotFoundException should be thrown");
    }

}
