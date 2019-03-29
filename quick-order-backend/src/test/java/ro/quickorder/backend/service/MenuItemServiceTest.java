package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.repository.MenuItemRepository;

import javax.inject.Inject;


import java.util.List;

import static org.junit.Assert.*;

/**
 * @author R. Lupoaie
 */
@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuItemServiceTest {

    @Inject
    private MenuItemRepository menuItemRepository;
    @Inject
    private MenuItemService menuItemService;
    @Inject
    private MenuItemConverter menuItemConverter;


    @Before
    public void setUp(){

        MenuItem menuItem1 = new MenuItem("Name1", "Original description!", 12, 20);
        MenuItem menuItem2 = new MenuItem("Name2", "Original description!", 20, 30);
        MenuItem menuItem3 = new MenuItem("Name3", "Original description!", 25, 40);

        menuItemRepository.save(menuItem1);
        menuItemRepository.save(menuItem2);
        menuItemRepository.save(menuItem3);
    }

    @After
    public void tearDown(){
        menuItemRepository.deleteAll();
    }




    @Test
    public void testGetMenuItems() {
        List<MenuItemDto> menuItems = menuItemService.getMenuItems();

        assertEquals(3, menuItems.size());
    }

    @Test
    public void testAddMenuItem() {
        MenuItemDto menuItemDto= new MenuItemDto("Salad", "the most original description!", 5, 18);

        List<MenuItemDto> menuItems = menuItemService.getMenuItems();

        assertEquals(3, menuItems.size());

        menuItemService.addMenuItem(menuItemDto);

        List<MenuItemDto> newMenuItems = menuItemService.getMenuItems();

        assertEquals(4, newMenuItems.size());
    }

    @Test
    public void testAddMenuItemWhenItemNameAlreadyExists() {
        MenuItemDto menuItemDto= new MenuItemDto("Name1", "the most original description!", 5, 18);

        try {
            menuItemService.addMenuItem(menuItemDto);
            fail("MenuItem already exists, error should pop-up");
        } catch (NotFoundException e){
            assertEquals("MenuItem already exists!",  e.getMessage());
        }
    }

    @Test
    public void testUpdateMenuItem() {
        MenuItemDto menuItemDto= new MenuItemDto("Name1", "the most original description!", 5, 18);

        menuItemService.updateMenuItem(menuItemDto);

        MenuItem newMenuItem =  menuItemRepository.findByName(menuItemDto.getName());

        assertEquals(menuItemDto.getPrice(),newMenuItem.getPrice());
        assertEquals(menuItemDto.getDescription(),newMenuItem.getDescription());
        assertEquals(menuItemDto.getPreparationDurationInMinutes(),newMenuItem.getPreparationDurationInMinutes());
        assertEquals(menuItemDto.getPrice(),newMenuItem.getPrice());
    }

    @Test
    public void testUpdateMenuItemWhenUserDoseNotExist() {
        MenuItemDto menuItemDto= new MenuItemDto("Name", "the most original description!", 5, 18);

        try {
            menuItemService.updateMenuItem(menuItemDto);
            fail("User dose not exist, error should pop-up");
        } catch (NotFoundException e){
            assertEquals("MenuItem not found!", e.getMessage());
        }
    }

    @Test
    public void testRemoveMenuItem() {

        List<MenuItemDto> menuItems = menuItemService.getMenuItems();

        assertEquals(3, menuItems.size());

        menuItemService.removeMenuItem(menuItems.get(0).getName());

        List<MenuItemDto> newMenuItems = menuItemService.getMenuItems();

        assertEquals(2, newMenuItems.size());
    }

    @Test
    public void testRemoveMenuItemWhenMenuItemDoseNotExist() {
        MenuItemDto menuItemDto= new MenuItemDto("Name", "the most original description!", 5, 18);

        try {
            menuItemService.removeMenuItem(menuItemDto.getName());
            fail("User dose not exist, error should pop-up");
        } catch (NotFoundException e){
            assertEquals("MenuItem not found!", e.getMessage());
        }
    }
}