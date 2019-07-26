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
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author R. Lupoaie
 */
@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuItemTypeServiceTest {
    @Autowired
    MenuItemTypeService menuItemTypeService;
    @Autowired
    MenuItemTypeRepository menuItemTypeRepository;
    @Autowired
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
    public void tearDown() {
        menuItemTypeRepository.deleteAll();
    }

    @Test
    public void testGetAllMenuItemTypes() {
        List<MenuItemTypeDto> menuItemTypes = menuItemTypeService.getAllMenuItemTypes();
        assertEquals(3, menuItemTypes.size());
    }

    @Test
    public void testAddMenuItemType() {
        List<MenuItemTypeDto> menuItemTypes = menuItemTypeService.getAllMenuItemTypes();
        assertEquals(3, menuItemTypes.size());

        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto("fructe");
        menuItemTypeService.addMenuItemType(menuItemTypeDto);

        List<MenuItemTypeDto> menuItemTypesAfter = menuItemTypeService.getAllMenuItemTypes();
        assertEquals(4, menuItemTypesAfter.size());
    }

    @Test(expected = BadRequestException.class)
    public void testAddMenuItemTypeWithTypeToShort() {
        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto("f");
        menuItemTypeService.addMenuItemType(menuItemTypeDto);
        fail("Type name is to short, it should fail!");
    }

    @Test(expected = NotFoundException.class)
    public void testAddMenuItemTypeWithTypeThatAlreadyExists() {
        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto("legume");
        menuItemTypeService.addMenuItemType(menuItemTypeDto);
        fail("Type name already exists, it should fail!");

    }
}