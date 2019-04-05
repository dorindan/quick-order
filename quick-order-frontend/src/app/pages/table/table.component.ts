import { Component, OnInit } from '@angular/core';
import {MenuItem} from '../../models/MenuItem';
import {Observable} from 'rxjs';
import {Ingredient} from '../../models/Ingredient';
import {MatTableDataSource} from '@angular/material';
import {MenuService} from '../../services/menu.service';
import {IngredientService} from '../../services/ingredient.service';
import {Table} from '../../models/Table';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent implements OnInit {

  menuItems: Table[];
  menuItemsGet: Observable<Table[]>;
  displayedColumns: string[] = ['name', 'description', 'ingredients', 'preparationTime', 'price', 'edit'];
  dataSource = new MatTableDataSource<Table>(this.menuItems);

  nameRight = true;
  priceRight = true;
  durationRight = true;

  name = '';
  description = '';
  preparationDurationInMinutes = 0;
  ingredients = [];
  price = 0;

  constructor(private tableService: MenuService, private ingredientService: IngredientService) {
  }

  ngOnInit() {
    this.ingredientsList = [];
    this.ingredientGet = this.ingredientService.getIngredient();
    this.ingredientGet.forEach(menuItem => menuItem.forEach(m => {
      this.ingredientsList.push(m);
    }));
    this.updateMenu();
  }

  updateMenu() {
    this.menuItemsGet = this.tableService.getMenuItems();
    this.menuItems = [];
    this.menuItemsGet.forEach(menuItem => menuItem.forEach(m => {
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
      newMenuItem = new MenuItem(this.name, this.description, this.preparationDurationInMinutes, this.ingredients, this.price);

      this.tableService.addMenuItem(newMenuItem);
      window.location.reload();
    } else {
      alert('Some date are not valid, try again!');
    }
  }

  update(): void {
    if (this.validation()) {
      let newMenuItem: MenuItem;
      newMenuItem = new MenuItem(this.name, this.description, this.preparationDurationInMinutes, this.ingredients, this.price);
      this.tableService.editMenuItem(newMenuItem);
      window.location.reload();
    } else {
      alert('Some Date are not valid, try again!');
    }
  }

  delete(): void {
    this.tableService.deleteMenuItem(this.name);
    window.location.reload();
  }

  clear(): void {
    this.name = '';
    this.description = '';
    this.preparationDurationInMinutes = 0;
    this.ingredients = [];
    this.price = 0;
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
