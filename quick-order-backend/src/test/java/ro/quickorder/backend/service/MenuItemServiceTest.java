package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.converter.IngredientConverter;
import ro.quickorder.backend.converter.MenuItemConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItem;
import ro.quickorder.backend.model.MenuItemType;
import ro.quickorder.backend.model.dto.IngredientDto;
import ro.quickorder.backend.model.dto.MenuItemDto;
import ro.quickorder.backend.model.dto.MenuItemTypeDto;
import ro.quickorder.backend.repository.IngredientRepository;
import ro.quickorder.backend.repository.MenuItemRepository;
import ro.quickorder.backend.repository.MenuItemTypeRepository;

import javax.inject.Inject;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private IngredientRepository ingredientRepository;
    @Inject
    private MenuItemService menuItemService;
    @Inject
    private IngredientService ingredientService;
    @Inject
    private MenuItemConverter menuItemConverter;
    @Inject
    private IngredientConverter ingredientConverter;
    @Inject
    private MenuItemTypeRepository menuItemTypeRepository;



    @Before
    public void setUp(){


        MenuItemType menuItemType = new MenuItemType("desert");

        MenuItem menuItem1 = new MenuItem("Name1", "Original description!", 12, 20, menuItemType);
        MenuItem menuItem2 = new MenuItem("Name2", "Original description!", 20, 30, menuItemType);
        MenuItem menuItem3 = new MenuItem("Name3", "Original description!", 25, 40, menuItemType);
        Ingredient ingredient1 = new Ingredient("marar");
        Ingredient ingredient2 = new Ingredient("sare");
        Ingredient ingredient3 = new Ingredient("piper");


        menuItemTypeRepository.save(menuItemType);

        menuItemRepository.save(menuItem1);
        menuItemRepository.save(menuItem2);
        menuItemRepository.save(menuItem3);

        ingredientRepository.save(ingredient1);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(ingredient3);
    }

    @After
    public void tearDown(){

        menuItemRepository.deleteAll();
        ingredientRepository.deleteAll();
        menuItemTypeRepository.deleteAll();
    }




    @Test
    public void testGetMenuItems() {
        List<MenuItemDto> menuItems = menuItemService.getMenuItems();

        assertEquals(3, menuItems.size());
    }

    @Test
    public void testAddMenuItem() {
        MenuItemDto menuItemDto= new MenuItemDto("Salad", "the most original description!", 5, 18, new MenuItemTypeDto("desert"));

        List<IngredientDto> ingredientDtos = ingredientService.getAll();

        Set<IngredientDto> ingredientDtoSet = new HashSet<>();

        ingredientDtoSet.add(ingredientDtos.get(1));
        ingredientDtoSet.add(ingredientDtos.get(2));

        menuItemDto.setIngredients(ingredientDtoSet);

        List<MenuItemDto> menuItems = menuItemService.getMenuItems();

        assertEquals(3, menuItems.size());

        menuItemService.addMenuItem(menuItemDto);

        List<MenuItemDto> newMenuItems = menuItemService.getMenuItems();

        assertEquals(4, newMenuItems.size());

        assertEquals(2, newMenuItems.get(3).getIngredients().size());
    }

    @Test
    public void testAddMenuItemWhenNameIsNull() {
        MenuItemDto menuItemDto= new MenuItemDto();
        try {
            menuItemService.addMenuItem(menuItemDto);
            fail("MenuItem has name equals with null, error should pop-up");
        } catch (BadRequestException e){
            assertEquals("Name can not be null",  e.getMessage());
        }
    }

    @Test
    public void testAddMenuItemWhenTypeIsNotFound() {
        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto("dose not exist");
        MenuItemDto menuItemDto= new MenuItemDto("Salad", "the most original description!", menuItemTypeDto, null, 5, 18);
        try {
            menuItemService.addMenuItem(menuItemDto);
            fail("MenuItem has has type that should not been found");
        } catch (NotFoundException e){
            assertEquals("MenuItemType was not found!",  e.getMessage());
        }
    }

    @Test
    public void testAddMenuItemWhenItemNameAlreadyExists() {
        MenuItemDto menuItemDto= new MenuItemDto("Name1", "the most original description!", 5, 18, new MenuItemTypeDto("desert"));

        try {
            menuItemService.addMenuItem(menuItemDto);
            fail("MenuItem already exists, error should pop-up");
        } catch (BadRequestException e){
            assertEquals("MenuItem already exists!",  e.getMessage());
        }
    }

    @Test
    public void testUpdateMenuItem() {
        MenuItemDto menuItemDto= new MenuItemDto("Name1", "the most original description!", 5, 18, new MenuItemTypeDto("desert"));

        menuItemService.updateMenuItem(menuItemDto);

        MenuItem newMenuItem =  menuItemRepository.findByName(menuItemDto.getName());

        assertEquals(menuItemDto.getPrice(),newMenuItem.getPrice());
        assertEquals(menuItemDto.getDescription(),newMenuItem.getDescription());
        assertEquals(menuItemDto.getPreparationDurationInMinutes(),newMenuItem.getPreparationDurationInMinutes());
        assertEquals(menuItemDto.getPrice(),newMenuItem.getPrice());
    }

    @Test
    public void testUpdateMenuItemWhenUserDoseNotExist() {
        MenuItemDto menuItemDto= new MenuItemDto("Name", "the most original description!", 5, 18, new MenuItemTypeDto("desert"));

        try {
            menuItemService.updateMenuItem(menuItemDto);
            fail("User dose not exist, error should pop-up");
        } catch (NotFoundException e){
            assertEquals("MenuItem not found!", e.getMessage());
        }
    }

    @Test
    public void testUpdateMenuItemWhenTypeIsNotFound() {
        MenuItemTypeDto menuItemTypeDto = new MenuItemTypeDto("dose not exist");
        MenuItemDto menuItemDto= new MenuItemDto("Name1", "the most original description!", menuItemTypeDto, null, 5, 18);
        try {
            menuItemService.updateMenuItem(menuItemDto);
            fail("MenuItem has has type that should not been found");
        } catch (NotFoundException e){
            assertEquals("MenuItemType was not found!",  e.getMessage());
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
        MenuItemDto menuItemDto= new MenuItemDto("Name", "the most original description!", 5, 18, new MenuItemTypeDto("desert"));

        try {
            menuItemService.removeMenuItem(menuItemDto.getName());
            fail("User dose not exist, error should pop-up");
        } catch (NotFoundException e){
            assertEquals("MenuItem not found!", e.getMessage());
        }
    }
}