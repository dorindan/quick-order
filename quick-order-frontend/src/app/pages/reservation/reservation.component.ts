import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReservationService} from '../../services/reservation.service';
import {MatDatepickerInputEvent} from '@angular/material';
import {Reservation} from '../../models/Reservation';
import {MatSnackBar} from '@angular/material/snack-bar';
import {PropertyService} from '../../services/property.service';
import {Table} from '../../models/Table';
import {TableService} from '../../services/table.service';
import {TokenStorageService} from '../../auth/token-storage.service';
import {User} from '../../models/User';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import * as moment from 'moment'

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss'],

})
export class ReservationComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  isEditable = true;
  date = '';
  month: number;
  time = '00:00';
  dateTime: string;
  nrOfPersons: number;
  reservation: Reservation;
  events: string[] = [];
  currentDate = new Date();
  hours: string[] = [];
  hourControl = new FormControl('', [Validators.required]);
  disableButton: boolean = false;

  public tableList: Table[] = [];
  public selectedTables = [];

  constructor(private translateService: TranslateService,
              private _formBuilder: FormBuilder,
              private reservationService: ReservationService,
              private snackBar: MatSnackBar,
              private propertyService: PropertyService,
              private tableService: TableService,
              private tokenStorageService: TokenStorageService,
              private router: Router) {
  }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.propertyService.getBistroProperty().subscribe(response => {
      const startHours: string[] = response.startProgramTime.split(':', 3);
      const startHour = new Date();
      startHour.setHours(+startHours[0], +startHours[1], +startHours[2]);
      const endHours: string[] = response.endProgramTime.split(':', 3);
      const endHour = new Date();
      endHour.setHours(+endHours[0], +endHours[1], +endHours[2]);
      this.fillHours(startHour, endHour);
    });
  }
/*
4/9/2019 08:05
21+8+2019+08:05
 */
  updateTables() {
    this.dateTime = this.date.concat(' ').concat(this.time);
    const checkInTimeFormatted = moment(this.dateTime, "D/M/YYYY HH:mm").format("D+M+YYYY+HH:mm");
    const outHouer = Number(this.time.split(':')[0]) + 2;
    this.dateTime = this.date.concat(' ').concat(outHouer + ':' + this.time.split(':')[1]);
    const checkOutTimeFormatted = moment(this.dateTime, "D/M/YYYY HH:mm").format("D+M+YYYY+HH:mm");
    if (this.isAuthenticatedWaiter()){
      this.tableService.getTables(checkInTimeFormatted, checkOutTimeFormatted).subscribe(rez => {
        this.tableList = rez;
      });
    }
  }

  addDate(type: string, event: MatDatepickerInputEvent<Date>) {
    this.events.push(`${type}: ${event.value}`);
    this.month = event.value.getMonth() + 1;
    this.date = event.value.getDate() + '/' + this.month + '/' + event.value.getFullYear();
  }

  onChange(event) {
    this.time = event.value;
  }

  addNrOfPersons(event) {
    this.nrOfPersons = event.valueOf();
  }

  concatenate() {
    this.dateTime = this.date.concat(' ').concat(this.time);
    const username = this.tokenStorageService.getUsername();
    const authorities = this.tokenStorageService.getAuthorities();
    const user = new User(username.toString(), '');
    user.roles = authorities;
    this.reservation = new Reservation(this.dateTime, this.dateTime, this.nrOfPersons, 'add', false, user);
    let i = -1;
    this.disableButton = true;
    const tablesSelected: Table[] = [];
    for (i = 0; i < this.tableList.length; i++) {
      if (this.contains(this.tableList[i].tableNr)) {
        tablesSelected.push(this.tableList[i]);
      }
    }
    if (this.isAuthenticatedWaiter()) {
      this.reservation.tableFoodDtos = tablesSelected;
      if (this.reservation.tableFoodDtos.length === 0 || this.calculateSeats(this.reservation.tableFoodDtos) >= this.nrOfPersons) {
        this.reservationService.reserve(this.reservation)
          .subscribe(data => {
            this.router.navigate(['loggedStart']);
            this.showSnackbar(this.translateService.instant('reservation.reservationSuccessful'));
          }, error => {
            this.disableButton = false;
            switch (error.status) {
              case 403: // forbidden exception
                this.showSnackbar(this.translateService.instant('reservationError.wrongDateOrNrOfPersons'));
                break;
              default:
                this.showSnackbar(this.translateService.instant('reservationError.fail'));
            }
          });
      } else {
        this.disableButton = false;
        this.showSnackbar(this.translateService.instant('reservationError.personsFitInTable'));
      }
    } else {
      this.reservationService.reserve(this.reservation)
        .subscribe(data => {
          this.router.navigate(['loggedStart']);
          this.showSnackbar(this.translateService.instant('reservation.reservationSuccessful'));
        }, error => {
          this.disableButton = false;
          this.showSnackbar(this.translateService.instant('reservationError.fail'));
        });
    }
  }

  calculateSeats(tables: Table[]): number {
    let nr = 0;
    let i = 0;
    for (i = 0; i < tables.length; i++) {
      nr = nr + tables[i].seats;
    }
    return nr;
  }

  contains(comparer: number): boolean {
    let i = 0;
    for (i = 0; i < this.selectedTables.length; i++) {
      if (Number(this.selectedTables[i]) === comparer) {
        return true;
      }
    }
    return false;
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      panelClass: ['snackbar']
    });
  }

  isAuthenticatedWaiter() {
    return this.tokenStorageService.isAuthenticatedWithRole('ROLE_WAITER');
  }

  validateHour(time: string) {
    const splittedHour = time.split(':', 2);
    const splittedDate = this.date.split('/', 3);
    const day = +splittedDate[0];
    const month = +splittedDate[1];
    const year = +splittedDate[2];
    const hour = +splittedHour[0];
    const minutes = +splittedHour[1];
    if (this.currentDate.getFullYear() < year) {
      return true;
    }
    if (this.currentDate.getMonth() + 1 < month) {
      return true;
    }
    if (this.currentDate.getDate() < day) {
      return true;
    }
    if (hour < this.currentDate.getHours()) {
      return false;
    }
    return !(hour === this.currentDate.getHours() && minutes <= this.currentDate.getMinutes());
  }

  fillHours(startHour: Date, finishHour: Date) {
    let i: number;
    let j: number;
    for (i = startHour.getHours(); i < finishHour.getHours(); i++) {
      for (j = 0; j <= 55; j += 5) {
        if (j === 0) {
          this.hours.push(i + ':' + '00');
        } else if (j === 5) {
          this.hours.push(i + ':' + '05');
        } else {
          this.hours.push(i + ':' + j);
        }
      }
    }
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.key;
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
  }
}
