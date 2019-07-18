import {Component, HostListener, OnInit} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {MenuItemType} from '../../models/MenuItemType';
import {MenuItem} from '../../models/MenuItem';
import {Router} from '@angular/router';
import {Command} from '../../models/Command';
import {CommandService} from '../../services/command.service';
import {MatSnackBar} from '@angular/material';
import {MenuItemCommand} from '../../models/MenuItemCommand';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  public amounts = [];
  public commandActive = false;
  public menuItemType: MenuItemType[];
  public menuItems: MenuItem[];
  public command: Command;
  public newCommand: Command = new Command();
  public totalAmount = 0;

  constructor(private menuService: MenuService, private router: Router,
              private snackBar: MatSnackBar, private commandService: CommandService) {
  }

  ngOnInit() {
    this.menuService.getMenuItemType().subscribe(response => {
      this.menuItemType = response;
    });

    this.menuService.getMenuItems().subscribe(response => {
      this.menuItems = response;
      response.forEach(i => this.amounts.push(1));
    });

    this.commandOpen();
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  calculateAmount() {
    this.totalAmount = 0;
    for (const item of this.command.menuItemCommandDtos) {
      this.totalAmount += item.amount;
    }
  }

  commandOpen() {
    this.commandService.hasActiveCommand('admin').subscribe(res => {
      this.command = res;
      if (this.command.menuItemCommandDtos == null) {
        this.command.menuItemCommandDtos = [];
      }
      this.calculateAmount();
      this.newCommand = this.command;
      this.newCommand.menuItemCommandDtos = [];
      if (res !== null && res.status === 'ACTIVE') {
        this.commandActive = true;
      }
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
      const commandMenuItem = new MenuItemCommand();
      commandMenuItem.amount = this.amounts[amountIndex];
      commandMenuItem.menuItemDto = menuItem;
      this.newCommand.menuItemCommandDtos.push(commandMenuItem);
    }
    this.totalAmount += this.amounts[amountIndex];
    this.showSnackbar(this.amounts[amountIndex] + ' ' + menuItem.name + ' successfully added');
    this.amounts[amountIndex] = 1;
  }

  public saveCommand() {
    this.commandService.updateCommand(this.newCommand);
  }

  finishCommand() {
    this.saveCommand();
    this.router.navigate(['finishCommand']);
  }

}
