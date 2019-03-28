import {Ingredient} from "./Ingredient";

export class MenuItem{
  get ingredients(): Ingredient[] {
    return this._ingredients;
  }

  set ingredients(value: Ingredient[]) {
    this._ingredients = value;
  }
  private _id:number;
  private _name:string;
  private _description:string;
  private _preparationDurationInMinutes:number;
  private _ingredients: Ingredient[];
  private _price:number;


  constructor(id: number, name: string, description: string, preparationDurationInMinutes: number, ingredients: Ingredient[], price: number) {
    this._id = id;
    this._name = name;
    this._description = description;
    this._preparationDurationInMinutes = preparationDurationInMinutes;
    this._ingredients = ingredients;
    this._price = price;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get description(): string {
    return this._description;
  }

  set description(value: string) {
    this._description = value;
  }

  get preparationDurationInMinutes(): number {
    return this._preparationDurationInMinutes;
  }

  set preparationDurationInMinutes(value: number) {
    this._preparationDurationInMinutes = value;
  }

  get price(): number {
    return this._price;
  }

  set price(value: number) {
    this._price = value;
  }
}
