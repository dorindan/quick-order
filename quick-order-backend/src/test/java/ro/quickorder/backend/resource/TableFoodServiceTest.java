package ro.quickorder.backend.resource;


import org.h2.table.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;
import ro.quickorder.backend.services.TableFoodService;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class TableFoodServiceTest {

    @Inject
    TableFoodRepository tableFoodRepository;
    @Inject
    TableFoodService tableFoodService;

    @Before
    public void setUp() {
        TableFood table1 = new TableFood(1,5,false,1,true);
        TableFood table2 = new TableFood(2,4,true,1,false);
        TableFood table3 = new TableFood(3,4,false,1,true);
        TableFood table4 = new TableFood(4,6,true,1,false);
        TableFood table5 = new TableFood(5,5,false,1,true);

        tableFoodRepository.save(table1);
        tableFoodRepository.save(table2);
        tableFoodRepository.save(table3);
        tableFoodRepository.save(table4);
        tableFoodRepository.save(table5);
    }

    @After
    public void tearDown(){
        tableFoodRepository.deleteAll();
    }

    @Test
    public void testgetAllTables(){

        List<TableFoodDto> rezFree = tableFoodService.getAllFree();

        assertEquals(rezFree.size(),3);
    }





}
