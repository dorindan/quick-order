import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReservationService} from '../../services/reservation.service';
import {MatDatepickerInputEvent} from '@angular/material';
import {Reservation} from '../../models/Reservation';
import {MatSnackBar} from '@angular/material/snack-bar';
import {PropertyService} from '../../services/property.service';

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

  constructor(private _formBuilder: FormBuilder,
              private reservationService: ReservationService,
              private snackBar: MatSnackBar,
              private propertyService: PropertyService) {
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
    this.reservation = new Reservation(this.dateTime, this.dateTime, this.nrOfPersons, 'add', false);
    this.reservationService.reserve(this.reservation)
      .subscribe(data => {
        this.showSnackbar('Reservation sent successfully.');
      }, Error => {
        this.showSnackbar('Reservation failed. Please try again.');
      });
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      panelClass: ['snackbar']
    });
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
