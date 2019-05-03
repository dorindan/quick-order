import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from '@angular/material';
import {Ingredient} from '../../models/Ingredient';
import {MenuItem} from '../../models/MenuItem';
import {Observable} from 'rxjs';
import {MenuService} from '../../services/menu.service';
import {IngredientService} from '../../services/ingredient.service';
import {MenuItemType} from '../../models/MenuItemType';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss']
})
export class MenuItemComponent implements OnInit {

  menuItems: MenuItem[];
  menuItemsGet: Observable<MenuItem[]>;
  ingredientsList: Ingredient[];
  ingredientGet: Observable<Ingredient[]>;
  menuItemTypes: MenuItemType[];
  menuItemTypesGet: Observable<MenuItemType[]>;
  displayedColumns: string[] = ['name', 'description', 'type', 'ingredients', 'preparationTime', 'price', 'edit'];
  dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
  nameRight = true;
  priceRight = true;
  durationRight = true;
  name = '';
  description = '';
  itemType = '';
  preparationDurationInMinutes = 0;
  ingredients = [];
  price = 0;
  activateIngredientAdd = false;
  activateTypeAdd = false;
  ingredientToAdd = '';
  menuItemTypeToAdd = '';

  constructor(private menuItemService: MenuService, private ingredientService: IngredientService) {
  }

  ngOnInit() {
    this.ingredientsList = [];
    this.ingredientGet = this.ingredientService.getIngredient();
    this.ingredientGet.forEach(menuItem => menuItem.forEach(m => {
      this.ingredientsList.push(m);
    }));
    this.updateMenu();
    this.updateMenuItemType();
  }

  updateMenu() {
    this.menuItemsGet = this.menuItemService.getMenuItems();
    this.menuItems = [];
    this.menuItemsGet.forEach(menuItem => menuItem.forEach(m => {
      if (m.menuItemTypeDto == null) {
        m.menuItemTypeDto = new MenuItemType('');
      }
      this.menuItems.push(m);
      this.dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
    }));
  }

  setUpdate(menuItem: MenuItem): void {
    this.name = menuItem.name;
    this.description = menuItem.description;
    this.preparationDurationInMinutes = menuItem.preparationDurationInMinutes;
    menuItem.ingredients.forEach(i => {
      this.ingredients.push(i);
    });
    this.price = menuItem.price;
  }

  setDelete(menuItem: MenuItem): void {
    this.name = menuItem.name;
  }

  add(): void {
    if (this.validation()) {
      let newMenuItem: MenuItem;
      const itemTypeToUse = new MenuItemType(this.itemType);
      newMenuItem = new MenuItem(this.name, this.description,
        this.preparationDurationInMinutes, this.ingredients, this.price, itemTypeToUse);
      this.menuItemService.addMenuItem(newMenuItem);
      window.location.reload();
    } else {
      alert('Some date are not valid, try again!');
    }
  }

  update(): void {
    if (this.validation()) {
      let newMenuItem: MenuItem;
      const itemTypeToUse = new MenuItemType(this.itemType);
      newMenuItem = new MenuItem(this.name, this.description,
        this.preparationDurationInMinutes, this.ingredients, this.price, itemTypeToUse);
      this.menuItemService.editMenuItem(newMenuItem);
      window.location.reload();
    } else {
      alert('Some Date are not valid, try again!');
    }
  }

  updateMenuItemType(): void {
    this.menuItemTypesGet = this.menuItemService.getMenuItemType();
    this.menuItemTypes = [];
    this.menuItemTypesGet.forEach(menuItemType => menuItemType.forEach(m => {
      this.menuItemTypes.push(m);
    }));
  }

  delete(): void {
    this.menuItemService.deleteMenuItem(this.name);
    window.location.reload();
  }

  clear(): void {
    this.name = '';
    this.description = '';
    this.preparationDurationInMinutes = 0;
    this.ingredients = [];
    this.price = 0;
    this.itemType = '';
    this.activateIngredientAdd = false;
    this.activateTypeAdd = false;
    this.ingredientToAdd = '';
    this.menuItemTypeToAdd = '';
  }

  addIngredient(): void {
    if (this.activateIngredientAdd) {
      const ingredient = new Ingredient(this.ingredientToAdd);
      this.ingredientService.addIngredient(ingredient).subscribe(rez => {
        this.ingredientsList.push(ingredient);
      }, error1 => {
        alert('The ingredient could not be added!, Please try again!');
      });
      this.activateIngredientAdd = false;
      this.ingredientToAdd = '';
    } else {
      this.activateIngredientAdd = true;
    }
  }

  addItemType(): void {
    if (this.activateTypeAdd) {
      const itemType = new MenuItemType(this.menuItemTypeToAdd);
      this.menuItemService.addMenuItemType(itemType).subscribe(rez => {
        this.menuItemTypes.push(itemType);
      }, error1 => {
        alert('The ingredient could not be added!, Please try again!');
      });
      this.activateTypeAdd = false;
      this.menuItemTypeToAdd = '';
    } else {
      this.activateTypeAdd = true;
    }
  }

  clearIngredient(): void {
    this.activateIngredientAdd = false;
    this.ingredientToAdd = '';
  }

  clearMenuItemType(): void {
    this.activateTypeAdd = false;
    this.menuItemTypeToAdd = '';
  }

  validation(): boolean {
    if (this.name.length > 2) {
      this.nameRight = true;
    } else {
      this.nameRight = false;
    }
    if (this.price < 0) {
      this.priceRight = false;
    } else {
      this.priceRight = true;
    }
    if (this.preparationDurationInMinutes < 0) {
      this.durationRight = false;
    } else {
      this.durationRight = true;
    }
    if (!this.durationRight || !this.nameRight || !this.priceRight) {
      return false;
    } else {
      return true;
    }
  }
}
