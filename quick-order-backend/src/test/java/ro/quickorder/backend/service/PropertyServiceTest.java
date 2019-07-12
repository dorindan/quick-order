package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.PropertyName;
import ro.quickorder.backend.model.dto.PropertyDto;
import ro.quickorder.backend.repository.PropertyRepository;

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
    public void setUP(){
        Property startTimeProperty = new Property(PropertyName.START_TIME, "10");
        Property endTimeProperty = new Property(PropertyName.END_TIME, "18");

        propertyRepository.save(startTimeProperty);
        propertyRepository.save(endTimeProperty);
    }

    @After
    public void tearDown(){
        propertyRepository.deleteAll();
    }

    @Test
    public void findScheduleTest(){
        PropertyDto programDto = propertyService.findProperties();

        Assert.assertEquals(10, programDto.getStartProgramTime().getHour());
        Assert.assertEquals(18, programDto.getEndProgramTime().getHour());
    }

    @Test
    public void findScheduleNoStartTimeTest(){
        propertyRepository.deleteAll();

        Property endTimeProperty = new Property(PropertyName.END_TIME, "18");
        propertyRepository.save(endTimeProperty);

        try {
            PropertyDto programDto = propertyService.findProperties();
            fail("There should be no start property!");
        } catch(NullPointerException e){
            Assert.assertEquals("Start date was not found!", e.getMessage());
        }
    }

    @Test
    public void findScheduleNoEndTimeTest(){
        propertyRepository.deleteAll();

        Property startTimeProperty = new Property(PropertyName.START_TIME, "18");
        propertyRepository.save(startTimeProperty);

        try {
            PropertyDto programDto = propertyService.findProperties();
            fail("There should be no end property!");
        } catch(NullPointerException e){
            Assert.assertEquals("End date was not found!", e.getMessage());
        }
    }

    @Test
    public void findScheduleBadValueInDatabaseTest(){
        propertyRepository.deleteAll();

        Property startTimeProperty = new Property(PropertyName.START_TIME, "10");
        Property endTimeProperty = new Property(PropertyName.END_TIME, "18 break");

        propertyRepository.save(startTimeProperty);
        propertyRepository.save(endTimeProperty);

        try {
            PropertyDto programDto = propertyService.findProperties();
            fail("Error should appear, one of the values is not a number!");
        } catch(NullPointerException e){
            Assert.assertEquals("Value is not a number!", e.getMessage());
        }
    }

}
