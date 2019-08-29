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

  constructor(private commandService: CommandService,
              private snackBar: MatSnackBar,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit() {
    if (this.isAuthenticatedWaiter()) {
      this.commandService.getAllUnconfirmed().subscribe (res => {
        this.commands = res;
      });
    } else if (this.isAuthenticatedUser()) {
      this.commandService.getCommandsOfUser().subscribe (res => {
        this.commands = res;
      });
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
    this.commandService.confirmCommand(command).subscribe(data => {
        this.showSnackbar('Successfully confirmed');
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
  }

  deleteCommand(commandName: string) {
    this.commandService.deleteCommand(commandName).subscribe(data => {
        this.showSnackbar('Successfully deleted');
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
  }
}
