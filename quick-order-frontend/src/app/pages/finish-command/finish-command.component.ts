import {Component, OnInit} from '@angular/core';
import {MenuItemType} from '../../models/MenuItemType';
import {MenuItem} from '../../models/MenuItem';
import {Command} from '../../models/Command';
import {MenuService} from '../../services/menu.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {CommandService} from '../../services/command.service';
import {MenuItemCommand} from '../../models/MenuItemCommand';

@Component({
  selector: 'app-finish-command',
  templateUrl: './finish-command.component.html',
  styleUrls: ['./finish-command.component.scss']
})
export class FinishCommandComponent implements OnInit {
  public amounts = [];
  public commandActive = false;
  public command: Command;
  public newCommand = new Command();
  public totalAmount = 0;
  public menuItemCommands: MenuItemCommand[] = [];

  constructor(private menuService: MenuService, private router: Router,
              private snackBar: MatSnackBar, private commandService: CommandService) {
  }

  ngOnInit() {
    this.commandService.hasActiveCommand('admin').subscribe(res => {
      this.command = res;
      if (res !== null) {
        if (this.command.menuItemCommandDtos == null) {
          this.command.menuItemCommandDtos = [];
        }
        this.menuItemCommands = this.command.menuItemCommandDtos;
        this.calculateAmount();
        this.newCommand = this.command;
        this.newCommand.menuItemCommandDtos = [];
        if (res !== null && res.status === 'ACTIVE') {
          this.commandActive = true;
        }
      }
    });
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

  deleteMenuItemFromCommand(menuItem: MenuItem) {
    for (const item of this.menuItemCommands) {
      if (menuItem.name === item.menuItemDto.name) {
        this.menuItemCommands = this.menuItemCommands.filter(theItem => theItem.menuItemDto.name !== item.menuItemDto.name);
      }
    }
  }

  public saveCommand() {
    for (const item of this.command.menuItemCommandDtos) {
      let ok = false;
      for (const newItm of this.menuItemCommands) {
        if (item.menuItemDto === newItm.menuItemDto) {
          ok = true;
          const difference = item.amount - newItm.amount;
         // alert('Am gasit')
          if (difference > 0) {
            const tmp = item;
            tmp.amount = -difference;
            this.newCommand.menuItemCommandDtos.push(tmp);
          }
        } else {
          //alert('Nu am gasit');
        }
      }
      if (!ok) {
        const tmp = item;
        tmp.amount = -item.amount;
        this.newCommand.menuItemCommandDtos.push(tmp);
      }
    }
    this.commandService.updateCommand(this.newCommand).subscribe(res => {
      this.command.menuItemCommandDtos = this.menuItemCommands;
      this.newCommand.menuItemCommandDtos = [];
      this.showSnackbar('Command placed');
    }, error => {
      switch (error.status) {
        case 404: // not found exception
          this.showSnackbar('Something went wrong, refresh and try again.');
          break;
        default:
          this.showSnackbar('Something went wrong, Pleas try again.');
      }
    });
  }

  finishCommand() {
    this.newCommand.status = 'DONE';
    this.saveCommand();
    window.location.reload();
  }

  goToPlaceAnOrder() {

  }


  totalPrice(): number {
    let sum = 0;
    for (const menuItemCommand of this.menuItemCommands) {
      sum += menuItemCommand.amount * menuItemCommand.menuItemDto.price;
    }
    return sum;
  }
}
