package ro.quickorder.backend.model.dto;

import ro.quickorder.backend.model.TableFood;

import java.util.List;

public class TableFoodListDto {
    List<TableFoodDto> tableFoodList;

    public TableFoodListDto() {
    }

    public TableFoodListDto(List<TableFoodDto> tableFoodList) {
        this.tableFoodList = tableFoodList;
    }

    public List<TableFoodDto> getTableFoodList() {
        return tableFoodList;
    }

    public void setTableFoodList(List<TableFoodDto> tableFoodList) {
        this.tableFoodList = tableFoodList;
    }
}

