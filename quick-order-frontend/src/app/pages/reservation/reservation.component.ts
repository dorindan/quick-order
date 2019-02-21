import {Component, EventEmitter, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormControl, Validators} from '@angular/forms'
import {ReservationService} from "../../services/reservation.service";
import {MatDatepickerInputEvent, MatOptionSelectionChange, MatSelectChange} from "@angular/material";
import {Reservation} from "../../models/Reservation";

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
  time="00:00";
  dateTime:string;
  nrOfPersons: number;
  reservation:Reservation;



  constructor(private _formBuilder: FormBuilder,
              private reservationService:ReservationService) {}

  ngOnInit() {
    this.firstFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

  events: string[] = [];
  addDate(type: string, event: MatDatepickerInputEvent<Date>) {
    this.events.push(`${type}: ${event.value}`);
    this.month = event.value.getMonth() + 1;
    this.date = event.value.getDate() + "/" + this.month + "/" + event.value.getFullYear();
  }

  onChange(event) {
    this.time="";
    this.time=event.value.name;
  }

  addNrOfPersons(event){
    this.nrOfPersons= event.valueOf();
  }

  concatenate(){

    this.dateTime="";
    this.dateTime = this.date.concat(" ").concat(this.time);
    this.reservation = new Reservation(this.dateTime,this.nrOfPersons);
    console.log(this.reservation);
  }

  hourControl = new FormControl('', [Validators.required]);
  selectFormControl = new FormControl('', Validators.required)

  hours: Hour[] = [
    {name: '10:00'},
    {name: '10:30'},
    {name: '11:00'},
    {name: '11:30'},
    {name: '12:00'},
    {name: '12:30'},
    {name: '13:00'},
    {name: '13:30'},
    {name: '14:00'},
    {name: '14:30'},
    {name: '15:00'},
    {name: '15:30'},
    {name: '16:00'},
    {name: '16:30'},
    {name: '17:00'},
    {name: '17:30'},
    {name: '18:00'},
    {name: '18:30'},
    {name: '19:00'},
    {name: '19:30'},
    {name: '20:00'},
    {name: '20:30'},
    {name: '21:00'},
    {name: '21:30'},
    {name: '22:00'}
  ];



}
