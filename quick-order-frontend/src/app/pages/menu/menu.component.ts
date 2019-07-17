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
    this.commandService.hasActiveCommand('sad').subscribe(res => {
      this.command = res;
      if (res !== null && res.status === 'Active') {
        this.commandActive = true;
      }
    });
  }

  addToCommand(menuItem: MenuItem, amountIndex: number) {
      const commandMenuItem = new CommandMenuItem();
      commandMenuItem.amount = this.amounts[amountIndex];
      commandMenuItem.menuItem = menuItem;
      this.command.menuItems.push(commandMenuItem);
    if (this.amounts[amountIndex] > 1) {
      this.showSnackbar(this.amounts[amountIndex] + ' items successfully added');
    } else {
      this.showSnackbar(this.amounts[amountIndex] + ' item successfully added');
    }
    this.amounts[amountIndex] = 1;
  }

  public saveCommand() {
    this.commandService.updateCommand(this.command);
  }

  finishCommand() {
    this.saveCommand();
    this.router.navigate(['finishCommand']);
  }

}
