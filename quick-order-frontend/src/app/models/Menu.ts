import {MenuItem} from './MenuItem';
import {Ingredient} from './Ingredient';

export class Menu {
  constructor(id: number, type: string, menuItems: MenuItem[], ingredients: Ingredient[]) {
    this._id = id;
    this._type = type;
    this._menuItems = menuItems;
    this._ingredients = ingredients;
  }

  private _id: number;

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  private _type: string;

  get type(): string {
    return this._type;
  }

  set type(value: string) {
    this._type = value;
  }

  private _menuItems: MenuItem[];

  get menuItems(): MenuItem[] {
    return this._menuItems;
  }

  set menuItems(value: MenuItem[]) {
    this._menuItems = value;
  }

  private _ingredients: Ingredient[];

  get ingredients(): Ingredient[] {
    return this._ingredients;
  }

  set ingredients(value: Ingredient[]) {
    this._ingredients = value;
  }
}
