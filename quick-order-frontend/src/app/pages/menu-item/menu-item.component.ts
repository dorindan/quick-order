import {Component, OnInit} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {MatSelectChange, MatTableDataSource} from '@angular/material';
import {Ingredient} from '../../models/Ingredient';
import {MenuItem} from '../../models/MenuItem';
import {Observable} from 'rxjs';
import {Reservation} from '../../models/Reservation';
import {MenuService} from '../../services/menu.service';


@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss']
})
export class MenuItemComponent implements OnInit {

  menuItems: MenuItem[];
  menuItemsGet: Observable<MenuItem[]>;
  displayedColumns: string[] = ['name', 'description', 'ingredients', 'preparationTime', 'price', 'edit'];
  dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
  selection = new SelectionModel<MenuItem>(true, []);
  ingredientsList: Ingredient[];


  name = '';
  description = '';
  preparationDurationInMinutes = 0;
  ingredients = [];
  price = 0;

  constructor(private tableService: MenuService) {
  }

  ngOnInit() {
    this.ingredientsList = [];
    this.ingredientsList.push(new Ingredient('piper'));
    this.ingredientsList.push(new Ingredient('sare'));
    this.ingredientsList.push(new Ingredient('zahar'));
    this.ingredientsList.push(new Ingredient('oua'));
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

  add(): void {
    let newMenuItem: MenuItem;
    alert(' |' + this.ingredients.length + '| ');
    newMenuItem = new MenuItem(this.name, this.description, this.preparationDurationInMinutes, this.ingredients, this.price);

    this.tableService.addMenuItem(newMenuItem);
    this.clear();
    this.updateMenu();
  }

  setUpdate(menuItem: MenuItem): void {
    this.name = menuItem.name;
    this.description = menuItem.description;
    this.preparationDurationInMinutes = menuItem.preparationDurationInMinutes;
    this.ingredients = menuItem.ingredients;
    this.price = menuItem.price;
  }

  setDelete(menuItem: MenuItem): void {
    this.name = menuItem.name;
  }

  update(): void {
    let newMenuItem: MenuItem;
    newMenuItem = new MenuItem(this.name, this.description, this.preparationDurationInMinutes, this.ingredients, this.price);
    alert(this.name + ' ' + this.description + ' ' + this.preparationDurationInMinutes + ' ' + this.ingredients.length + ' ' + this.price);
    this.tableService.editMenuItem(newMenuItem);
    this.clear();
  }

  delete(): void {
    this.tableService.deleteMenuItem(this.name);
    this.clear();
    this.updateMenu();
  }

  clear(): void {
    this.name = '';
    this.description = '';
    this.preparationDurationInMinutes = 0;
    this.ingredients = [];
    this.price = 0;
  }

}
