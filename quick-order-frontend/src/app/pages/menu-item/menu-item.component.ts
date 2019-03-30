import {Component, OnInit} from '@angular/core';
import {SelectionModel} from '@angular/cdk/collections';
import {MatTableDataSource} from '@angular/material';
import {Ingredient} from '../../models/Ingredient';
import {MenuItem} from '../../models/MenuItem';




@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.scss']
})
export class MenuItemComponent implements OnInit {

  menuItems: MenuItem[] = [];
  displayedColumns: string[] = [ 'select', 'name', 'description', 'ingredients', 'preparationTime', 'price'];
  dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
  selection = new SelectionModel<MenuItem>(true, []);

  ngOnInit(): void {
    const ingredient = new Ingredient('numele e tare');
   const menuItem1= new MenuItem('Name', 'Description',  9, null , 21);
   const menuItem2 = new MenuItem('Name', 'Description',  9, null , 21);
   const menuItem3 = new MenuItem('Name', 'Description',  9, null , 21);
   const menuItem4 = new MenuItem('Name', 'Description',  9, null , 21);
   const menuItem = new MenuItem('Name', 'Description',  9, null , 21);
   menuItem.ingredients = [];
   menuItem.ingredients.push(ingredient);
   menuItem.ingredients.push(ingredient);
   menuItem.ingredients.push(ingredient);
    this.menuItems.push(menuItem);
    this.menuItems.push(menuItem1);
    this.menuItems.push(menuItem2);
    this.menuItems.push(menuItem4);
    this.menuItems.push(menuItem3);
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

  add(_name: string, _description: string, _preparationDurationInMinutes: number): void {
    alert(name + _description + _preparationDurationInMinutes);
  }

  update(_name: string, _description: string, _preparationDurationInMinutes: number): void {
    alert(name + _description + _preparationDurationInMinutes);
  }

  remove(_name: string, _description: string, _preparationDurationInMinutes: number): void {
    alert(name + _description + _preparationDurationInMinutes);
  }
}
