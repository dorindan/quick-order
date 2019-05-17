package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.repository.PropertyRepository;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author R. Lupoaie
 */

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyServiceTest {

    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    PropertyService propertyService;

    @Before
    public void setUp() {
        LocalTime start = LocalTime.of(10, 00);
        LocalTime end = LocalTime.of(20, 00);

        Property property = new Property(start, end);

        propertyRepository.save(property);
    }


    @After
    public void tearDown() {
        propertyRepository.deleteAll();
    }

    @Test
    public void testFindByRestaurant(){
        PropertyDto propertyDto = propertyService.findByRestaurant();
        LocalTime start = LocalTime.of(10, 00);
        LocalTime end = LocalTime.of(20, 00);
        assertEquals(start, propertyDto.getStartProgramTime());
        assertEquals(end, propertyDto.getEndProgramTime());
    }

    @Test
    public void testFindByRestaurantToMany(){
        LocalTime start = LocalTime.of(10, 00);
        LocalTime end = LocalTime.of(20, 00);

        Property property = new Property(start, end);

        propertyRepository.save(property);
        try {
            PropertyDto propertyDto = propertyService.findByRestaurant();
            fail("There are to many properties, there should be an error");
        } catch (NotFoundException e) {
            assertEquals("There are to many properties!", e.getMessage());
        }
    }

    @Test
    public void testFindByRestaurantNoProperty(){
        propertyRepository.deleteAll();

        try {
            PropertyDto propertyDto = propertyService.findByRestaurant();
            fail("There are no property, there should be an error");
        } catch (NotFoundException e) {
            assertEquals("There is no property!", e.getMessage());
        }
    }

}
