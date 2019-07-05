package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.PropertyConverter;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.PropertyName;
import ro.quickorder.backend.model.dto.ProgramDto;
import ro.quickorder.backend.repository.PropertyRepository;

import java.time.LocalTime;

@Service
public class PropertyService {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);
    @Autowired
    PropertyConverter propertyConvertor;
    @Autowired
    PropertyRepository propertyRepository;

    public ProgramDto findSchedule() {

        Property startProperty = propertyRepository.findByName(PropertyName.START_TIME);
        if(startProperty == null){
            LOG.error("Start date was not found!");
            throw new NullPointerException("Start date was not found!");
        }
        Property endProperty = propertyRepository.findByName(PropertyName.END_TIME);
        if(endProperty == null){
            LOG.error("End date was not found!");
            throw new NullPointerException("End date was not found!");
        }

        int startHour;
        int endHour;
        try {
            startHour = Integer.parseInt(startProperty.getValue());
            endHour = Integer.parseInt(endProperty.getValue());
        } catch (NumberFormatException  e){
            LOG.error("Value is not a number!");
            throw new NullPointerException("Value is not a number!");
        }

        LocalTime startProgramTime = LocalTime.of(startHour,0,0,0);
        LocalTime endProgramTime = LocalTime.of(endHour,0,0,0);
        return new ProgramDto(startProgramTime,endProgramTime);
    }
}
