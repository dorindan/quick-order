package ro.quickorder.backend.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.model.Reservation;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableFoodService {

    @Autowired
    private TableFoodConverter tableFoodConverter;

    @Autowired
    private ReservationConverter reservationConverter;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private TableFoodRepository tableFoodRepository;



    public List<TableFoodDto> getAllFree(Timestamp checkInTime, Timestamp checkOutTime) {
        if(checkInTime == null || checkOutTime == null){
            throw new BadRequestException("Time parameters can not be null");
        }

        List<TableFoodDto> rez = new ArrayList<>();
        List<TableFood> tables = tableFoodRepository.findAll();

        List<TableFood> reservation1 = reservationRepository.findReservationsByInside(checkInTime,checkOutTime);

        for(TableFood tableFood: reservation1) {
            if (tables.contains(tableFood))
                tables.remove(tableFood);
        }

        for (TableFood table : tables) {
            rez.add(tableFoodConverter.toTableFoodDto(table));
        }
        return rez;
    }

}
