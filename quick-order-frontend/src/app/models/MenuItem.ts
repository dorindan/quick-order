import {Ingredient} from './Ingredient';

export class MenuItem {
  public name: string;
  public description: string;
  public preparationDurationInMinutes: number;
  public ingredients: Ingredient[] = [];
  public price: number;

  constructor(newName: string, newDescription: string, newPreparationDurationInMinutes: number,
              nerIngredients: Ingredient[], newPrice: number) {
    this.name = newName;
    this.description = newDescription;
    this.preparationDurationInMinutes = newPreparationDurationInMinutes;
    this.ingredients = nerIngredients;
    this.price = newPrice;
  }
}
