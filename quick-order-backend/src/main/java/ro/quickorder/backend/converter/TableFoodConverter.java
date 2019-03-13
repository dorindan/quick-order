package ro.quickorder.backend.converter;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.TableFood;
import ro.quickorder.backend.model.dto.TableFoodDto;

@Component
public class TableFoodConverter {

    public TableFood convertTableFoodDtoToTableFood(TableFoodDto tableFoodDto){
        TableFood tableFood= new TableFood();
        tableFood.setTableNr(tableFoodDto.getTableNr());
        tableFood.setSeats(tableFoodDto.getSeats());
        tableFood.setWindowView(tableFoodDto.isWindowView());
        tableFood.setFloor(tableFoodDto.getFloor());
        tableFood.setFree(tableFoodDto.isFree());
        return tableFood;
    }

    public TableFoodDto convertTableFoodToTableFoodDto(TableFood tableFood){
        TableFoodDto tableFoodDto= new TableFoodDto();
        tableFoodDto.setTableNr(tableFood.getTableNr());
        tableFoodDto.setSeats(tableFood.getSeats());
        tableFoodDto.setWindowView(tableFood.isWindowView());
        tableFoodDto.setFloor(tableFood.getFloor());
        tableFoodDto.setFree(tableFood.isFree());
        return tableFoodDto;
    }


}
