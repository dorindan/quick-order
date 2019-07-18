import {Component, HostListener, OnInit} from '@angular/core';
import {MenuService} from '../../services/menu.service';
import {MenuItemType} from '../../models/MenuItemType';
import {MenuItem} from '../../models/MenuItem';
import {Router} from '@angular/router';
import {Command} from '../../models/Command';
import {CommandService} from '../../services/command.service';
import {MatSnackBar} from '@angular/material';
import {CommandMenuItem} from '../../models/CommandMenuItem';

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

  commandOpen() {
    this.commandService.hasActiveCommand('admin').subscribe(res => {
      this.command = res;
      if (this.command.commandMenuItemDtos == null) {
        this.command.commandMenuItemDtos = [];
      }
      this.newCommand = this.command;
      this.newCommand.commandMenuItemDtos = [];
      if (res !== null && res.status === 'ACTIVE') {
        this.commandActive = true;
      }
    });
  }

  addToCommand(menuItem: MenuItem, amountIndex: number) {
    let exist = false;
    for (const item of this.newCommand.commandMenuItemDtos) {
      if (item.menuItemDto.name === menuItem.name) {
        item.amount += this.amounts[amountIndex];
        exist = true;
      }
    }
    if (!exist) {
      const commandMenuItem = new CommandMenuItem();
      commandMenuItem.amount = this.amounts[amountIndex];
      commandMenuItem.menuItemDto = menuItem;
      this.newCommand.commandMenuItemDtos.push(commandMenuItem);
    }
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
