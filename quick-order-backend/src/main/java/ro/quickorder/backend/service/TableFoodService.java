package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.ReservationConverter;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableFoodService {

    private static final Logger LOG = LoggerFactory.getLogger(TableFoodService.class);

    @Autowired
    private TableFoodConverter tableFoodConverter;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private TableFoodRepository tableFoodRepository;

    public List<TableFoodDto> getAllFree(Timestamp checkInTime, Timestamp checkOutTime) {
        if (checkInTime == null || checkOutTime == null) {
            LOG.error("Time parameters can not be null");
            throw new BadRequestException("Time parameters can not be null");
        }

        List<TableFoodDto> rez = new ArrayList<>();
        List<TableFood> tables = tableFoodRepository.findAll();

        List<TableFood> busyTableFoods = reservationRepository.findTablesWithReservationsBetween(checkInTime, checkOutTime);

        for (TableFood busyTableFood : busyTableFoods) {
            if (tables.contains(busyTableFood))
                tables.remove(busyTableFood);
        }

        for (TableFood table : tables) {
            rez.add(tableFoodConverter.toTableFoodDto(table));
        }
        return rez;
    }

    public List<TableFoodDto> getAll(){
        List<TableFoodDto> rez = new ArrayList<>();
        List<TableFood> tables = tableFoodRepository.findAll();
        for (TableFood table: tables)
            rez.add(tableFoodConverter.toTableFoodDto(table));
        return rez;
    }

}
