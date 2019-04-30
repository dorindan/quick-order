package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.TableFoodConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
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
    ReservationRepository reservationRepository;
    @Autowired
    private TableFoodConverter tableFoodConverter;
    @Autowired
    private TableFoodRepository tableFoodRepository;

    public List<TableFoodDto> getAllFree(Timestamp checkInTime, Timestamp checkOutTime) {
        if (checkInTime == null || checkOutTime == null) {
            LOG.error("Time parameters can not be null");
            throw new BadRequestException("Time parameters can not be null");
        }
        List<TableFoodDto> allFreeTables = new ArrayList<>();
        List<TableFood> tables = tableFoodRepository.findAll();
        List<TableFood> occupiedTableFoods = reservationRepository.findTablesWithReservationsBetween(checkInTime, checkOutTime);
        occupiedTableFoods.forEach(tables::remove);
        tables.stream().map(tableFoodConverter::toTableFoodDto).forEach(allFreeTables::add);
        return allFreeTables;
    }

    public List<TableFoodDto> getAll() {
        List<TableFoodDto> allTables = new ArrayList<>();
        tableFoodRepository.findAll().stream().map(tableFood -> tableFoodConverter.toTableFoodDto(tableFood)).forEach(allTables::add);
        return allTables;
    }

    public void addTable(TableFoodDto tableFoodDto){
        TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
        if(tableFood != null){
            LOG.error("Table already exists");
            throw new BadRequestException("Table already exists");
        }
        tableFood = tableFoodConverter.toTableFood(tableFoodDto);
        tableFoodRepository.save(tableFood);
    }

    public void updateTable(TableFoodDto tableFoodDto){
        TableFood tableFood = tableFoodRepository.findByTableNr(tableFoodDto.getTableNr());
        if(tableFood == null){
            LOG.error("Table not found");
            throw new NotFoundException("Table not found");
        }
        tableFood.setFloor(tableFoodDto.getFloor());
        tableFood.setWindowView(tableFoodDto.isWindowView());
        tableFood.setSeats(tableFoodDto.getSeats());
        tableFoodRepository.save(tableFood);
    }

    public void removeTable( int tableNr){
        TableFood tableFood = tableFoodRepository.findByTableNr(tableNr);
        if(tableFood == null){
            LOG.error("Table not found");
            throw new NotFoundException("Table not found");
        }
        tableFood.setActive(false);
        tableFoodRepository.save(tableFood);
    }

}
