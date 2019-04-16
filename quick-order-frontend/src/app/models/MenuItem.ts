import {Ingredient} from './Ingredient';

export class MenuItem {
  constructor(id: number, name: string, description: string, preparationDurationInMinutes: number,
              ingredients: Ingredient[], price: number) {
    this._id = id;
    this._name = name;
    this._description = description;
    this._preparationDurationInMinutes = preparationDurationInMinutes;
    this._ingredients = ingredients;
    this._price = price;
  }

  private _id: number;

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  private _name: string;

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  private _description: string;

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }

  private _preparationDurationInMinutes: number;

  get preparationDurationInMinutes(): number {
    return this._preparationDurationInMinutes;
  }

  set preparationDurationInMinutes(value: number) {
    this._preparationDurationInMinutes = value;
  }

  private _ingredients: Ingredient[];

  get ingredients(): Ingredient[] {
    return this._ingredients;
  }

  set ingredients(value: Ingredient[]) {
    this._ingredients = value;
  }

  private _price: number;

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }
}
