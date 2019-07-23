import {Component, Inject, OnInit} from '@angular/core';
import {MenuItemType} from '../../models/MenuItemType';
import {MenuItem} from '../../models/MenuItem';
import {Command} from '../../models/Command';
import {MenuService} from '../../services/menu.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {CommandService} from '../../services/command.service';
import {MenuItemCommand} from '../../models/MenuItemCommand';
import {LOCAL_STORAGE, WebStorageService} from 'angular-webstorage-service';

@Component({
  selector: 'app-finish-command',
  templateUrl: './finish-command.component.html',
  styleUrls: ['./finish-command.component.scss']
})
export class FinishCommandComponent implements OnInit {
  public amounts = [];
  public newCommand = new Command();
  public totalAmount = 0;
  private amountBeforChange = 0;

  constructor(@Inject(LOCAL_STORAGE) private storage: WebStorageService, private menuService: MenuService, private router: Router,
              private snackBar: MatSnackBar, private commandService: CommandService) {
  }

  ngOnInit() {
    this.reloadCommand();
  }

  reloadCommand() {
    if (this.storage.get('Command')) {
      this.newCommand = this.storage.get('Command') as Command;
      this.calculateTotalAmount();
    } else {
      this.newCommand.menuItemCommandDtos = [];
    }
  }
  calculateTotalAmount() {
    for (const item of this.newCommand.menuItemCommandDtos) {
      this.totalAmount += (item as MenuItemCommand).amount;
    }
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  deleteMenuItemFromCommand(menuItem: MenuItem) {
    for (const item of this.newCommand.menuItemCommandDtos) {
      if (menuItem.name === item.menuItemDto.name) {
        this.newCommand.menuItemCommandDtos =
          this.newCommand.menuItemCommandDtos.filter(theItem => theItem.menuItemDto.name !== item.menuItemDto.name);
      }
    }
    this.saveCommandInSession();
  }

  saveCommandInSession() {
    this.storage.set('Command', this.newCommand);
  }

  finishCommand() {
    window.location.reload();
  }

  totalPrice(): number {
    let sum = 0;
    for (const menuItemCommand of this.newCommand.menuItemCommandDtos) {
      sum += menuItemCommand.amount * menuItemCommand.menuItemDto.price;
    }
    return sum;
  }
}
