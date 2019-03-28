import {MenuItem} from "./MenuItem";
import {Ingredient} from "./Ingredient";

export class Menu{
  private _id: number;
  private _type:string;
  private _menuItems:MenuItem[];

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get type(): string {
    return this._type;
  }

  set type(value: string) {
    this._type = value;
  }

  get menuItems(): MenuItem[] {
    return this._menuItems;
  }

  set menuItems(value: MenuItem[]) {
    this._menuItems = value;
  }

  get ingredients(): Ingredient[] {
    return this._ingredients;
  }

  set ingredients(value: Ingredient[]) {
    this._ingredients = value;
  }

  private _ingredients:Ingredient[];

  constructor(id: number, type: string, menuItems: MenuItem[], ingredients: Ingredient[]) {
    this._id = id;
    this._type = type;
    this._menuItems = menuItems;
    this._ingredients = ingredients;
  }
}
