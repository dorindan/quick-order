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
  ingredients: string[] = [];
  price = 0;
  activateIngredientAdd = false;
  activateTypeAdd = false;
  ingredientToAdd = '';
  menuItemTypeToAdd = '';

  constructor(private menuItemService: MenuService, private ingredientService: IngredientService, private snackBar: MatSnackBar) {
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
    this.menuItemService.getMenuItems().subscribe(rez => {
      this.menuItems = rez;
      this.dataSource = new MatTableDataSource<MenuItem>(this.menuItems);
    });
  }

  updateMenuItemType(): void {
    this.menuItemService.getMenuItemType().subscribe(rez => {
      this.menuItemTypes = rez;
    });
  }

  setUpdate(menuItem: MenuItem): void {
    this.ingredients = [];
    this.name = menuItem.name;
    this.description = menuItem.description;
    this.preparationDurationInMinutes = menuItem.preparationDurationInMinutes;
    menuItem.ingredients.forEach(i => {
      this.ingredients.push(i.name);
    });
    this.itemType = menuItem.menuItemTypeDto.type;
    this.price = menuItem.price;

    this.clearMenuItemType();
    this.clearIngredient();
  }

  setDelete(menuItem: MenuItem): void {
    this.name = menuItem.name;
  }

  add(): void {
    if (this.validation()) {
      let newMenuItem: MenuItem;
      const itemTypeToUse = new MenuItemType(this.itemType);
      const selectedIngredients = this.ingredientsList
        .filter(value => this.ingredients.filter(value1 => value.name === value1).length > 0);
      newMenuItem = new MenuItem(this.name, this.description,
        this.preparationDurationInMinutes, selectedIngredients, this.price, itemTypeToUse);
      this.menuItemService.addMenuItem(newMenuItem)
        .subscribe(rez => {
          window.location.reload();
        }, error => {
          switch (error.status) {
            case 400: // bad request exception
              this.showSnackbar('Something went bad. Please try again!');
              break;
            case 404: // not found exception
              this.showSnackbar('The data could not been found. Please try again.');
              break;
            default:
              this.showSnackbar('The introduced data is not valid!, please try again!');
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
      const selectedIngredients = this.ingredientsList
        .filter(value => this.ingredients.filter(value1 => value.name === value1).length > 0);
      newMenuItem = new MenuItem(this.name, this.description,
        this.preparationDurationInMinutes, selectedIngredients, this.price, itemTypeToUse);
      this.menuItemService.editMenuItem(newMenuItem).subscribe(rez => {
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
    this.menuItemService.deleteMenuItem(this.name).subscribe(rez => {
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

  showIngredients(): string {
    let rez = '';
    if (this.ingredients.length > 0) {
      rez += this.ingredients[0];
    }
    if (this.ingredients.length > 1) {
      rez += ' (+' + (this.ingredients.length - 1);
      if (this.ingredients.length === 2) {
        rez += ' other)';
      } else {
        rez += ' others)';
      }
    }
    return rez;
  }

}
