import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {CommandService} from '../../services/command.service';
import {Observable} from 'rxjs';
import {Command} from '../../models/Command';
import {TokenStorageService} from '../../auth/token-storage.service';

@Component({
  selector: 'app-command-confirmation',
  templateUrl: './command-confirmation.component.html',
  styleUrls: ['./command-confirmation.component.scss']
})
export class CommandConfirmationComponent implements OnInit {
  private commandsGet: Observable<Command[]>;
  private commands: Command[];

  constructor(private commandSerivce: CommandService,
              private snackBar: MatSnackBar,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit() {
    if (this.isAuthenticatedWaiter()) {
      this.commandsGet = this.commandSerivce.getAllUnconfirmed();
      this.commands = [];
      this.commandsGet.forEach(command => command.forEach(r => this.commands.push(r)));
    } else if (this.isAuthenticatedUser()) {
      this.commandsGet = this.commandSerivce.getCommandsOfUser();
      this.commands = [];
      this.commandsGet.forEach(command => command.forEach(r => this.commands.push(r)));
    }
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  isAuthenticatedUser() {
    return this.tokenStorageService.isAuthenticatedWithRole('ROLE_USER');
  }

  isAuthenticatedWaiter() {
    return this.tokenStorageService.isAuthenticatedWithRole('ROLE_WAITER');
  }

  confirmCommand(command: Command) {
    this.commandSerivce.confirmCommand(command).subscribe(data => {
        this.showSnackbar('Successfully confirmed');
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
  }

  deleteCommand(commandName: string) {
    this.commandSerivce.deleteCommand(commandName).subscribe(data => {
        this.showSnackbar('Successfully deleted');
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
  }
}
