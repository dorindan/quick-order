package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.ReservationDto;
import ro.quickorder.backend.model.dto.TableFoodDto;
import ro.quickorder.backend.repository.TableFoodRepository;

import java.sql.Timestamp;
import java.util.List;
import ro.quickorder.backend.service.TableFoodService;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(value = "/table")
public class TableFoodResource {

    @Autowired
    TableFoodService tableFoodService;

    @RequestMapping(path = "/free", method = RequestMethod.GET)
    public List<TableFoodDto> getAllFree(Timestamp checkInTime, Timestamp checkOutTime){
        return tableFoodService.getAllFree(checkInTime,checkOutTime);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<TableFoodDto> getAllTables(){
        return tableFoodService.getAll();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public void addTable(@RequestBody @NotNull TableFoodDto tableFoodDto){
        tableFoodService.addTable(tableFoodDto);
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public void updateTable(@RequestBody @NotNull TableFoodDto tableFoodDto){
        tableFoodService.updateTable(tableFoodDto);
    }

    @RequestMapping(path = "/remove/{tableNr}", method = RequestMethod.DELETE)
    public void removeTable(@PathVariable int tableNr){
        tableFoodService.removeTable(tableNr);
    };



}
