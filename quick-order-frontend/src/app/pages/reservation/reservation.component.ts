import {Component, EventEmitter, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms';
import {ReservationService} from '../../services/reservation.service';
import {MatDatepickerInputEvent, MatOptionSelectionChange, MatSelectChange} from '@angular/material';
import {Reservation} from '../../models/Reservation';
import {MatSnackBar} from '@angular/material/snack-bar';

export interface Hour {
  name: string;
}

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


  constructor(private _formBuilder: FormBuilder,
              private reservationService: ReservationService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });


    var startHour:Date = new Date();
    startHour.setHours(10,0);

    var endHour:Date = new Date();
    endHour.setHours(22,0);
    this.fillHours(startHour,endHour);
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
    console.log(this.date);
    console.log(this.time);
    console.log(this.validateHour(this.currentDate.getHours() + ':' + this.currentDate.getMinutes()));
    this.dateTime = this.date.concat(' ').concat(this.time);



    this.reservation = new Reservation(this.dateTime, this.nrOfPersons);
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

  validateHour(time: string){
    var splittedHour = time.split(":",2);
    var splittedDate = this.date.split("/",3);
    var day = +splittedDate[0];
    var month = +splittedDate[1];
    var year = +splittedDate[2];
    var hour = +splittedHour[0];
    var minutes = +splittedHour[1];

    if (this.currentDate.getFullYear() < year){
      return true;
    }

    if (this.currentDate.getMonth() + 1 < month){
      return true;
    }

    if (this.currentDate.getDate() < day){
      return true;
    }

    if (hour < this.currentDate.getHours()){
      return false;
    }

    if (hour == this.currentDate.getHours() && minutes <= this.currentDate.getMinutes())
      return false;

    return true;
  }

  fillHours(startHour:Date, finishHour:Date){
    var i:number;
    var j:number;
    for (i = startHour.getHours(); i < finishHour.getHours(); i++){
      for (j = 0; j <= 55; j += 5){
        if (j == 0){
          this.hours.push(i + ':' + '00')
        }
        else if (j == 5){
          this.hours.push(i + ':' + '05')
        }
        else {
          this.hours.push(i + ':' + j)
        }
      }
    }
  }


  hourControl = new FormControl('', [Validators.required]);
  selectFormControl = new FormControl('', Validators.required);


  // hours: Hour[] = [
  //   {name: '8:00'},
  //   {name: '8:05'},
  //   {name: '8:10'},
  //   {name: '8:15'},
  //   {name: '8:20'},
  //   {name: '8:25'},
  //   {name: '8:30'},
  //   {name: '8:35'},
  //   {name: '8:40'},
  //   {name: '8:45'},
  //   {name: '8:50'},
  //   {name: '8:55'},
  //   {name: '9:00'},
  //   {name: '9:05'},
  //   {name: '9:10'},
  //   {name: '9:15'},
  //   {name: '9:20'},
  //   {name: '9:25'},
  //   {name: '9:30'},
  //   {name: '9:35'},
  //   {name: '9:40'},
  //   {name: '9:45'},
  //   {name: '9:50'},
  //   {name: '9:55'},
  //   {name: '10:00'},
  //   {name: '10:05'},
  //   {name: '10:10'},
  //   {name: '10:15'},
  //   {name: '10:20'},
  //   {name: '10:25'},
  //   {name: '10:30'},
  //   {name: '10:35'},
  //   {name: '10:40'},
  //   {name: '10:45'},
  //   {name: '10:50'},
  //   {name: '10:55'},
  //   {name: '11:00'},
  //   {name: '11:05'},
  //   {name: '11:10'},
  //   {name: '11:15'},
  //   {name: '11:20'},
  //   {name: '11:25'},
  //   {name: '11:30'},
  //   {name: '11:35'},
  //   {name: '11:40'},
  //   {name: '11:45'},
  //   {name: '11:50'},
  //   {name: '11:55'},
  //   {name: '12:00'},
  //   {name: '12:05'},
  //   {name: '12:10'},
  //   {name: '12:15'},
  //   {name: '12:20'},
  //   {name: '12:25'},
  //   {name: '12:30'},
  //   {name: '12:35'},
  //   {name: '12:40'},
  //   {name: '12:45'},
  //   {name: '12:50'},
  //   {name: '12:55'},
  //   {name: '13:00'},
  //   {name: '13:05'},
  //   {name: '13:10'},
  //   {name: '13:15'},
  //   {name: '13:20'},
  //   {name: '13:25'},
  //   {name: '13:30'},
  //   {name: '13:35'},
  //   {name: '13:40'},
  //   {name: '13:45'},
  //   {name: '13:50'},
  //   {name: '13:55'},
  //   {name: '14:00'},
  //   {name: '14:05'},
  //   {name: '14:10'},
  //   {name: '14:15'},
  //   {name: '14:20'},
  //   {name: '14:25'},
  //   {name: '14:30'},
  //   {name: '14:35'},
  //   {name: '14:40'},
  //   {name: '14:45'},
  //   {name: '14:50'},
  //   {name: '14:55'},
  //   {name: '15:00'},
  //   {name: '15:05'},
  //   {name: '15:10'},
  //   {name: '15:15'},
  //   {name: '15:20'},
  //   {name: '15:25'},
  //   {name: '15:30'},
  //   {name: '15:35'},
  //   {name: '15:40'},
  //   {name: '15:45'},
  //   {name: '15:50'},
  //   {name: '15:55'},
  //   {name: '16:00'},
  //   {name: '16:05'},
  //   {name: '16:10'},
  //   {name: '16:15'},
  //   {name: '16:20'},
  //   {name: '16:25'},
  //   {name: '16:30'},
  //   {name: '16:35'},
  //   {name: '16:40'},
  //   {name: '16:45'},
  //   {name: '16:50'},
  //   {name: '16:55'},
  //   {name: '17:00'},
  //   {name: '17:05'},
  //   {name: '17:10'},
  //   {name: '17:15'},
  //   {name: '17:20'},
  //   {name: '17:25'},
  //   {name: '17:30'},
  //   {name: '17:35'},
  //   {name: '17:40'},
  //   {name: '17:45'},
  //   {name: '17:50'},
  //   {name: '17:55'},
  //   {name: '18:00'},
  //   {name: '18:05'},
  //   {name: '18:10'},
  //   {name: '18:15'},
  //   {name: '18:20'},
  //   {name: '18:25'},
  //   {name: '18:30'},
  //   {name: '18:35'},
  //   {name: '18:40'},
  //   {name: '18:45'},
  //   {name: '18:50'},
  //   {name: '18:55'},
  //   {name: '19:00'},
  //   {name: '19:05'},
  //   {name: '19:10'},
  //   {name: '19:15'},
  //   {name: '19:20'},
  //   {name: '19:25'},
  //   {name: '19:30'},
  //   {name: '19:35'},
  //   {name: '19:40'},
  //   {name: '19:45'},
  //   {name: '19:50'},
  //   {name: '19:55'},
  //   {name: '20:00'},
  //   {name: '20:05'},
  //   {name: '20:10'},
  //   {name: '20:15'},
  //   {name: '20:20'},
  //   {name: '20:25'},
  //   {name: '20:30'},
  //   {name: '20:35'},
  //   {name: '20:40'},
  //   {name: '20:45'},
  //   {name: '20:50'},
  //   {name: '20:55'}
  // ];






  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.key;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;

  }
}
