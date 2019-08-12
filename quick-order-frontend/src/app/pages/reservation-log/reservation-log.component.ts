import {Component, OnInit} from '@angular/core';
import {ReservationService} from '../../services/reservation.service';
import {MatSnackBar} from '@angular/material';
import {Observable} from 'rxjs';
import {Reservation} from '../../models/Reservation';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-reservation-log',
  templateUrl: './reservation-log.component.html',
  styleUrls: ['./reservation-log.component.scss']
})
export class ReservationLogComponent implements OnInit {
  public reservationsGet: Observable<Reservation[]>;
  public reservations: Reservation[];

  constructor(
    private translateService: TranslateService,
    private reservationService: ReservationService,
    private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.reservationsGet = this.reservationService.getReservationOfUser();
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(r => this.reservations.push(r)));
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  deleteReservation(reservationName: string) {
    this.reservationService.deleteReservation(reservationName).subscribe(data => {
        this.showSnackbar(this.translateService.instant('myRes.successDelete'));
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
  }
}
