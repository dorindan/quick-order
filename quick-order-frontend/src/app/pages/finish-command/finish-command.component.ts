import {Component, Inject, OnInit} from '@angular/core';
import {MenuItem} from '../../models/MenuItem';
import {Command} from '../../models/Command';
import {MenuService} from '../../services/menu.service';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {CommandService} from '../../services/command.service';
import {MenuItemCommand} from '../../models/MenuItemCommand';
import {LOCAL_STORAGE, WebStorageService} from 'angular-webstorage-service';
import {User} from '../../models/User';
import {TokenStorageService} from '../../auth/token-storage.service';

@Component({
  selector: 'app-finish-command',
  templateUrl: './finish-command.component.html',
  styleUrls: ['./finish-command.component.scss']
})
export class FinishCommandComponent implements OnInit {
  public amounts = [];
  public command = new Command();
  public amount = [];
  public totalAmount = 0;
  public specification = '';
  public packed = false;
  public imgPath = [];

  constructor(@Inject(LOCAL_STORAGE) private storage: WebStorageService, private menuService: MenuService, private router: Router,
              private snackBar: MatSnackBar, private commandService: CommandService, private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    this.reloadCommand();
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  reloadCommand() {
    if (this.storage.get('command')) {
      const command = this.storage.get('command') as Command;
      this.commandService.updateMenuItemsFromCommand(command).subscribe(rez => {
        this.command = rez;
        if (this.command.menuItemCommandDtos === null) {
          this.command.menuItemCommandDtos = [];
          alert('menuITem sunt null');
        }
        this.calculateTotalAmount();
        for (const item of this.command.menuItemCommandDtos) {
          this.amount.push(item.amount);
          if (item.menuItemDto.img) {
            this.imgPath.push('assets/menuItemImg/' + item.menuItemDto.name + '.jpg');
          } else {
            this.imgPath.push('/assets/menuItemImg/default.jpg');
          }
        }
      });
    } else {
      this.command.menuItemCommandDtos = [];
    }
  }

  calculateTotalAmount() {
    for (const item of this.command.menuItemCommandDtos) {
      this.totalAmount += (item as MenuItemCommand).amount;
    }
  }

  deleteMenuItemFromCommand(menuItem: MenuItem) {
    for (let i = 0; i < this.command.menuItemCommandDtos.length; i++) {
      const item = this.command.menuItemCommandDtos[i];
      if (menuItem.name === item.menuItemDto.name) {
        this.command.menuItemCommandDtos =
          this.command.menuItemCommandDtos.filter(theItem => theItem.menuItemDto.name !== item.menuItemDto.name);
        this.imgPath = this.imgPath.filter(img => img !== this.imgPath[i]);
      }
    }
    this.saveCommandInSession();
  }

  saveCommandInSession() {
    this.storage.set('command', this.command);
  }

  finishCommand() {
    this.command.specification = this.specification;
    this.command.packed = this.packed;
    this.command.userDto = new User(this.tokenStorage.getUsername(), '');
    this.commandService.addCommand(this.command).subscribe(rez => {
      this.storage.set('command', new Command());
      this.router.navigate(['menu']);
    }, error => {
      if (error.status === 404) { // not found exception
        this.showSnackbar('Something went wrong, try again!');
      } else {
        this.showSnackbar('Something went wrong, try again!');
      }
    });
  }

  totalPrice(): number {
    let sum = 0;
    for (const menuItemCommand of this.command.menuItemCommandDtos) {
      sum += menuItemCommand.amount * menuItemCommand.menuItemDto.price;
    }
    return sum;
  }

  onAmountChange(amount: number, index: number) {
    if (amount > 0) {
      this.command.menuItemCommandDtos[index].amount = amount;
      this.saveCommandInSession();
    }
    this.amount[index] = this.command.menuItemCommandDtos[index].amount;
  }
}
