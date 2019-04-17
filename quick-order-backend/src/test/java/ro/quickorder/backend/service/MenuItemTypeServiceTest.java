package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.MenuItemTypeConverter;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author R. Lupoaie
 */
@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuItemTypeServiceTest {

    @Inject
    MenuItemTypeService menuItemTypeService;
    @Inject
    MenuItemTypeRepository menuItemTypeRepository;

    @Inject
    MenuItemTypeConverter menuItemTypeConverter;

    @Before
    public void setUp() {

        MenuItemType menuItemType1 = new MenuItemType("condiment");
        MenuItemType menuItemType2 = new MenuItemType("legume");
        MenuItemType menuItemType3 = new MenuItemType("carne");


        menuItemTypeRepository.save(menuItemType1);
        menuItemTypeRepository.save(menuItemType2);
        menuItemTypeRepository.save(menuItemType3);

    }

    @After
    public void tearDown(){
        menuItemTypeRepository.deleteAll();
    }


    @Test
    public void testGetAllMenuItemTypes() {
        List<MenuItemTypeDto> menuItemTypes = menuItemTypeService.getAllMenuItemTypes();

        assertEquals(3, menuItemTypes.size());
    }
}