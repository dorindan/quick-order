import {Component, OnInit} from '@angular/core';
import {ReservationService} from '../../services/reservation.service';
import {MatSnackBar} from '@angular/material';
import {Observable} from 'rxjs';
import {Reservation} from '../../models/Reservation';

@Component({
  selector: 'app-reservation-log',
  templateUrl: './reservation-log.component.html',
  styleUrls: ['./reservation-log.component.scss']
})
export class ReservationLogComponent implements OnInit {
  private reservationsGet: Observable<Reservation[]>;
  private reservations: Reservation[];

  constructor(private reservationService: ReservationService
    , private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.reservationsGet = this.reservationService.getReservationOfUser();
    this.reservations = [];
    this.reservationsGet.forEach(reservation => reservation.forEach(r => this.reservations.push(r)));
    console.log(this.reservations);
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
        this.showSnackbar('Successfully deleted');
        location.reload();
      },
      error => {
        this.showSnackbar(error.error.message);
      });
  }
}
