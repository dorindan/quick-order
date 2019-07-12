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
    }

    @After
    public void tearDown(){

    }



}
