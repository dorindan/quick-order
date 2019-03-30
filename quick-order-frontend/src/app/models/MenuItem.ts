import {Ingredient} from './Ingredient';

export class MenuItem {

  private _name: string;
  private _description: string;
  private _preparationDurationInMinutes: number;
  private _ingredients: Ingredient[];
  private _price: number;

  constructor(newName: string, newDescription: string, newPreparationDurationInMinutes: number, nerIngredients: Ingredient[], newPrice: number) {
    this._name = newName;
    this._description = newDescription;
    this._preparationDurationInMinutes = newPreparationDurationInMinutes;
    this._ingredients = nerIngredients;
    this._price = newPrice;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }
  get ingredients(): Ingredient[] {
    return this._ingredients;
  }

  set ingredients(value: Ingredient[]) {
    this._ingredients = value;
  }
  get preparationDurationInMinutes(): number {
    return this._preparationDurationInMinutes;
  }

  set preparationDurationInMinutes(value: number) {
    this._preparationDurationInMinutes = value;
  }
  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }
  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }


}
