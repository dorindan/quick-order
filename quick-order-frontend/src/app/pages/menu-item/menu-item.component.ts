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

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.data.forEach(row => this.selection.select(row));
  }

  add(): void {
    let newMenuItem: MenuItem;
    alert(' |' + this.ingredients.length + '| ');
    newMenuItem = new MenuItem(this.name, this.description, this.preparationDurationInMinutes, this.ingredients, this.price);

    this.tableService.addMenuItem(newMenuItem);
  }

  setupdate(menuItem: MenuItem): void {
    this.name = menuItem.name;
    this.description = menuItem.description;
    this.preparationDurationInMinutes = menuItem.preparationDurationInMinutes;
    this.ingredients = menuItem.ingredients;
    this.price = menuItem.price;
  }

  setdelete(menuItem: MenuItem): void {
    this.name = menuItem.name;
    this.description = menuItem.description;
    this.preparationDurationInMinutes = menuItem.preparationDurationInMinutes;
    this.ingredients = menuItem.ingredients;
    this.price = menuItem.price;
  }

  update(): void {
    let newMenuItem: MenuItem;
    newMenuItem = new MenuItem(this.name, this.description, this.preparationDurationInMinutes, this.ingredients, this.price);

    this.tableService.editMenuItem(newMenuItem);
  }

  delete(): void {
    this.tableService.deleteMenuItem(this.name);
  }

  clear(): void {
    this.name = '';
    this.description = '';
    this.preparationDurationInMinutes = 0;
    this.ingredients = [];
    this.price = 0;
  }

}
