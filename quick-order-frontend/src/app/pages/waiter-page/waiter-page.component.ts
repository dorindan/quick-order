import {Component, OnInit} from '@angular/core';
import {Table} from '../../models/Table';
import {TableService} from '../../services/table.service';
import {Observable} from 'rxjs';
import {ReservationService} from '../../services/reservation.service';
import {Reservation} from '../../models/Reservation';
import {ConfirmReservation} from '../../models/ConfirmReservation';
import {MatSnackBar} from '@angular/material';
import {TokenStorageService} from '../../auth/token-storage.service';
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-waiter-page',
  templateUrl: './waiter-page.component.html',
  styleUrls: ['./waiter-page.component.scss']
})
export class WaiterPageComponent implements OnInit {
  tablesGet: Observable<Table[]>;
  tables: Table[];
  tablesAssignedGet: Observable<Table[]>;
  reservationsGet: Observable<Reservation[]>;
  reservations: Reservation[];
  selectedOptions: Table[];
  indexExpanded: number;
  disabledElements: number[];
  totalOfSelectedSeats: number;
  private info: any;
  // contains the index if the obj is confirmed
  private actualConfirmed = -1;

  constructor(private translateService: TranslateService,
              private tableService: TableService,
              private reservationService: ReservationService,
              private snackBar: MatSnackBar, private token: TokenStorageService) {
  }

  ngOnInit() {
    this.info = {
      token: this.token.getToken(),
      username: this.token.getUsername(),
      authorities: this.token.getAuthorities()
    };
    this.reservationService.getAllReservations().subscribe(response => {
      let rezervarile: Reservation[] = this.sortReservations(response);
      this.reservations = rezervarile;
    });
    this.indexExpanded = -1;
    this.disabledElements = [];
    this.selectedOptions = [];
  }

  sortByConfirmation(reservationList: Reservation[]): Reservation[]{
    let newReservationList: Reservation[] = [];
    reservationList.forEach(reservation => {
      if (reservation.confirmed == true){
        newReservationList.push(reservation);
      }
      else newReservationList.unshift(reservation);
    })
    return newReservationList;
  }

  sortReservations(reservationList: Reservation[]): Reservation[]{
    let confirmedReservationList: Reservation[] = [];
    let unconfirmedReservationList: Reservation[] = [];
    reservationList.forEach(reservation => {
      let i: number = 0;
      if (reservation.confirmed == true){
        while (i < confirmedReservationList.length && this.compareDate(reservation.checkInTime,confirmedReservationList[i].checkInTime) > 0){
          i++;
        }
        confirmedReservationList.splice(i,0, reservation);
      }
      else{
        while (i < unconfirmedReservationList.length && this.compareDate(reservation.checkInTime,unconfirmedReservationList[i].checkInTime) > 0){
          i++;
        }
        unconfirmedReservationList.splice(i,0, reservation);
      }
    })
    return unconfirmedReservationList.concat(confirmedReservationList);
  }

  /*
  if the first date is bigger than second returns 1
  if the dates are equal returns 0
  if the second date is bigger than first date it returns -1
   */
  compareDate(dateAndHour1: string, dateAndHour2: string): number{
    let date1= dateAndHour1.split(' ',2)[0].split('/',3);
    let hour1= dateAndHour1.split(' ',2)[1].split(':',2);
    let date2= dateAndHour2.split(' ',2)[0].split('/',3);
    let hour2= dateAndHour2.split(' ',2)[1].split(':',2);

    if (date1[2] > date2[2]){
      return 1;
    }
    else if (date1[2] < date2[2]){
      return -1;
    }
    else {
      if (date1[1] > date2[1]){
        return 1;
      }
      else if (date1[1] < date2[1]){
        return -1;
      }
      else {
        if (date1[0] > date2[0]){
          return 1;
        }
        else if (date1[0] < date2[0]){
          return -1;
        }
        else{
          if (hour1[1] > hour2[1]){
            return 1;
          }
          else if (hour1[1] < hour2[1]){
            return-1;
          }
          else {
            if (hour1[0] > hour2[0]){
              return 1;
            }
            else if (hour1[0] < hour2[0]){
              return -1;
            }
            else{
              return 0;
            }
          }
        }
      }
    }
    return null;
  }
  

  getFormattedTime(reservation: Reservation): string {
    return reservation.checkInTime.substr(0, 10) + ' ' +
      reservation.checkInTime.substr(11, 5) + ' - ' +
      reservation.checkOutTime.substr(0, 10) + ' ' +
      reservation.checkOutTime.substr(11, 5);
  }

  acceptReservation(reservation: Reservation, index: number) {
    if (this.hint(reservation) === '') {

      if (this.selectedOptions.length === 0) {
        this.indexExpanded = -1;
      } else {
        this.indexExpanded = -1;
        this.disabledElements.push(index);
      }
      this.reservationService.confirmReservation(new ConfirmReservation(reservation.checkInTime,
        reservation.numberOfPersons, reservation.checkOutTime, reservation.reservationName, this.selectedOptions))
        .subscribe(data => {
          this.showSnackbar(this.translateService.instant('waiterPage.confirmed'));
        }, error => {
          switch (error.status) {
            case 404: // not found exception
              this.showSnackbar(this.translateService.instant('confirmReservationError.notFound'));
              break;
            default:
              this.showSnackbar(this.translateService.instant('confirmReservationError.fail'));
          }
        });
    }
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      panelClass: ['snackbar']
    });
  }

  hint(reservation: Reservation): String {
    this.totalOfSelectedSeats = 0;
    for (const option of this.selectedOptions) {
      for (const table of this.tables) {
        if (table.tableNr === option.tableNr) {
          this.totalOfSelectedSeats = this.totalOfSelectedSeats + table.seats;
        }
      }
    }
    if (reservation.numberOfPersons > this.totalOfSelectedSeats) {
      return 'You selected tables with less seats than requested.';
    }
    return '';
  }

  openGroup(any, index: number, reservation: Reservation) {
    this.selectedOptions = [];
    this.indexExpanded = index;
    let checkInTimeFormatted;
    checkInTimeFormatted = reservation.checkInTime.substr(0, 10).replace('/', '+').replace('/', '+')
      + '+' + reservation.checkInTime.substr(11, 5);
    let checkOutTimeFormatted;
    checkOutTimeFormatted = reservation.checkOutTime.substr(0, 10).replace('/', '+').replace('/', '+') + '+' +
      reservation.checkOutTime.substr(11, 5);

    // get all tables that are reserved by this reservation, if it is actualConfirmed
    this.tables = [];
    this.actualConfirmed = -1;
    this.tablesAssignedGet = this.tableService.getAllAssignedTablesOfAReservation(reservation.reservationName);
    this.tablesAssignedGet.forEach(table => table.forEach(t => {
      this.tables.push(t);
      if (this.actualConfirmed === -1) {
        this.actualConfirmed = index;
      }
    }));

    // get all free tables
    this.tablesGet = this.tableService.getTables(checkInTimeFormatted, checkOutTimeFormatted);
    this.tablesGet.forEach(table => table.forEach(t => this.tables.push(t)));

  }

  checkDisabled(reservation: Reservation): boolean {
    return reservation.confirmed;
  }

  enableEdit(i: number) {
    this.disabledElements = this.disabledElements.filter(item => item !== i);
  }

  closeGroup(): void {
    if (this.actualConfirmed !== -1) {
      this.disabledElements.push(this.actualConfirmed);
    }
  }

}

