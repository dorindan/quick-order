import {Ingredient} from './Ingredient';
import {MenuItemType} from './MenuItemType';

export class MenuItem {
  public name: string;
  public description: string;
  public preparationDurationInMinutes: number;
  public ingredients: Ingredient[] = [];
  public price: number;
  public menuItemTypeDto: MenuItemType;
  public img: boolean;

  constructor(newName: string, newDescription: string, newPreparationDurationInMinutes: number,
              nerIngredients: Ingredient[], newPrice: number, menuItemType: MenuItemType) {
    this.name = newName;
    this.description = newDescription;
    this.preparationDurationInMinutes = newPreparationDurationInMinutes;
    this.ingredients = nerIngredients;
    this.price = newPrice;
    this.menuItemTypeDto = menuItemType;
  }
}
