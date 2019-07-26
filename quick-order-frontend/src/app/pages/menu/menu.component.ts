import {Component, OnInit} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {MenuItemType} from '../../models/MenuItemType';
import {MenuItem} from '../../models/MenuItem';
import {Router} from '@angular/router';
import {Command} from '../../models/Command';
import {CommandService} from '../../services/command.service';
import {MatSnackBar} from '@angular/material';
import {MenuItemCommand} from '../../models/MenuItemCommand';
import {TokenStorageService} from '../../auth/token-storage.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  public amounts = [];
  public menuItemType: MenuItemType[];
  public menuItems: MenuItem[];
  public newCommand = new Command();
  public totalAmount = 0;

  constructor(private menuService: MenuService, private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.menuService.getMenuItemType().subscribe(response => {
      this.menuItemType = response;
    });

    this.menuService.getMenuItems().subscribe(response => {
      this.menuItems = response;
      response.forEach(i => this.amounts.push(1));
    });

    this.newCommand.menuItemCommandDtos = [];
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  addToCommand(menuItem: MenuItem, amountIndex: number) {
    if (this.amounts[amountIndex] < 1) {
      this.showSnackbar('Amount need to be greater than 0!');
      return;
    }
    let exist = false;
    for (const item of this.newCommand.menuItemCommandDtos) {
      if (item.menuItemDto.name === menuItem.name) {
        item.amount += this.amounts[amountIndex];
        exist = true;
      }
    }
    if (!exist) {
      const menuItemCommand = new MenuItemCommand();
      menuItemCommand.amount = this.amounts[amountIndex];
      menuItemCommand.menuItemDto = menuItem;
      this.newCommand.menuItemCommandDtos.push(menuItemCommand);
    }
    this.totalAmount += this.amounts[amountIndex];
    this.showSnackbar(this.amounts[amountIndex] + ' ' + menuItem.name + ' successfully added');
    this.amounts[amountIndex] = 1;
  }

  finishCommand() {
    this.router.navigate(['finishCommand']);
  }

}
