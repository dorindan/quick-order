import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material';
import {CommandService} from '../../services/command.service';

@Component({
  selector: 'app-command-confirmation',
  templateUrl: './command-confirmation.component.html',
  styleUrls: ['./command-confirmation.component.scss']
})
export class CommandConfirmationComponent implements OnInit {

  constructor(private commandSerivce: CommandService
    , private snackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }
}
