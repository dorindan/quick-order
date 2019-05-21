import {Component, OnInit} from '@angular/core';
import {MatSnackBar, MatTableDataSource} from '@angular/material';
import {Ingredient} from '../../models/Ingredient';
import {MenuItem} from '../../models/MenuItem';
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
  ingredientsList: Ingredient[];
  menuItemTypes: MenuItemType[];
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

  constructor(private tableService: MenuService, private ingredientService: IngredientService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.updateIngredients();
    this.updateMenu();
    this.updateMenuItemType();
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  updateIngredients() {
    this.ingredientService.getIngredient().subscribe(rez => {
      this.ingredientsList = rez;
      this.dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
    });
  }

  updateMenu() {
    this.tableService.getMenuItems().subscribe(rez => {
      this.menuItems = rez;
      this.dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
    });
  }

  updateMenuItemType(): void {
    this.tableService.getMenuItemType().subscribe(rez => {
      this.menuItemTypes = rez;
    });
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
      this.tableService.addMenuItem(newMenuItem)
        .subscribe(rez => {
          window.location.reload();
        }, error => {
          if (error.status === 400) { // bad request exception
            this.showSnackbar('The introduced data is not valid!, please try again!');
          } else if (error.status === 404) { // not found exception
            this.showSnackbar('The introduced data is not valid!, please try again');
          } else {
            this.showSnackbar('The introduced data is not valid!, please try again');
          }
        });
    } else {
      this.showSnackbar('The introduced data is not valid!, please try again!');
    }
  }

  update(): void {
    if (this.validation()) {
      let newMenuItem: MenuItem;
      const itemTypeToUse = new MenuItemType(this.itemType);
      newMenuItem = new MenuItem(this.name, this.description,
        this.preparationDurationInMinutes, this.ingredients, this.price, itemTypeToUse);
      this.tableService.editMenuItem(newMenuItem).subscribe(rez => {
        window.location.reload();
      }, error => {
        if (error.status === 404) { // not found exception
          this.showSnackbar('The introduced data is not valid!, please try again');
        } else {
          this.showSnackbar('The introduced data is not valid!, please try again');
        }
      });
    } else {
      this.showSnackbar('The introduced data is not valid!, please try again!');
    }
  }

  delete(): void {
    this.tableService.deleteMenuItem(this.name).subscribe(rez => {
      window.location.reload();
    }, error => {
      if (error.status === 404) { // not found exception
        this.showSnackbar('The item could not be deleted!, please try again!');
      } else {
        this.showSnackbar('The item could not be deleted!, please try again!');
      }
    });
  }

  clear(): void {
    this.name = '';
    this.description = '';
    this.preparationDurationInMinutes = 0;
    this.ingredients = [];
    this.price = 0;
    this.itemType = '';
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
