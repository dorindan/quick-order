import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {PropertyService} from "../../services/property.service";
import {Property} from "../../models/Property";
import {MatSnackBar} from "@angular/material";
import * as moment from 'moment'
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-property-administration',
  templateUrl: './property-administration.component.html',
  styleUrls: ['./property-administration.component.scss']
})
export class PropertyAdministrationComponent implements OnInit {

  startTime: string;
  endTime: string;
  location: string;
  locationLatitude: number;
  locationLongitude: number;
  email: string = '';
  restaurantName: string;
  rightEmail = true;
  rightStartTime = true;
  rightEndTime = true;
  rightLatitude = true;
  rightLongitude = true;

  constructor(private translateService: TranslateService,
              private snackBar: MatSnackBar,
              private propertyService: PropertyService) {

  }

  ngOnInit() {
    this.propertyService.getBistroProperty().subscribe(response => {
      this.startTime = response.startProgramTime.split(':', 3)[0];
      this.endTime = response.endProgramTime.split(':', 3)[0];
      this.email = response.email;
      this.locationLatitude = response.latitude;
      this.locationLongitude = response.longitude;
      this.location = response.streetName;
      this.restaurantName = response.restaurantName;
    });
  }

  public validateEmail(): void {
    this.rightEmail = this.isValidMailFormat(this.email);
  }

  public validateStartTime() {
    if (isNaN(Number(this.startTime))) {
      this.rightStartTime = false;
    }
    else if (!Number.isInteger(+this.startTime)) {
      this.rightStartTime = false;
    }
    else if (+this.startTime < 0 || +this.startTime > 24) {
      this.rightStartTime = false;
    }
    else {
      this.rightStartTime = true;
    }
  }

  public validateEndTime() {
    if (isNaN(Number(this.endTime))) {
      this.rightEndTime = false;
    }
    else if (!Number.isInteger(+this.endTime)) {
      this.rightEndTime = false;
    }
    else if (+this.endTime < 0 || +this.endTime > 24) {
      this.rightEndTime = false;
    }
    else {
      this.rightEndTime = true;
    }
  }

  public validateLatitude(){
    let control: FormControl;
    control = new FormControl(this.locationLatitude);
    const latitudeRegex = /^[-+]?[0-9]*\.?[0-9]+$/i;
    if (control.value != '' && latitudeRegex.test(control.value)){
      this.rightLatitude = true;
    }
    else{
      this.rightLatitude = false;
    }
  }

  public validateLongitude(){
    let control: FormControl;
    control = new FormControl(this.locationLongitude);
    const longitudeRegex = /^[-+]?[0-9]*\.?[0-9]+$/i;
    if (control.value != '' && longitudeRegex.test(control.value)){
      this.rightLongitude = true;
    }
    else{
      this.rightLongitude = false;
    }
  }

  private isValidMailFormat(email: string): boolean {
    let control: FormControl;
    control = new FormControl(email);
    const EMAIL_REGEXP = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/i;
    return !(control.value === '' || (control.value.length <= 5 || !EMAIL_REGEXP.test(control.value)));
  }


  isFloat(v) {
    var num = /^[-+]?[0-9]+\.[0-9]+$/;
    return num.test(v);
  }

  isInt(num) {
    return num === +num && num === (num | 0);
  }

  updateProperties() {

    let startMoment = moment().hours(Number(this.startTime)).minutes(0).seconds(0).format("HH:mm:ss");
    let endMoment = moment().hours(Number(this.endTime)).minutes(0).seconds(0).format("HH:mm:ss");

    let property = new Property(this.restaurantName, startMoment, endMoment,
      this.location, this.locationLatitude, this.locationLongitude, this.email);
    if (!this.isFloat(this.locationLongitude)) {
      this.showSnackbar(this.translateService.instant('propertyError.wrongLongitude'));
    }
    else if (!this.isFloat(this.locationLatitude)) {
      this.showSnackbar(this.translateService.instant('propertyError.wrongLatitude'));
    }
    else if (!this.rightEmail) {
      this.showSnackbar(this.translateService.instant('propertyError.emailNotValid'));
    }
    else if (!this.rightStartTime) {
      this.showSnackbar(this.translateService.instant('propertyError.wrongStartTime'));
    }
    else if (!this.rightEndTime) {
      this.showSnackbar(this.translateService.instant('propertyError.wrongEndTime'));
    }
    else this.propertyService.setBistroProperty(property).subscribe(response => {
          this.showSnackbar(this.translateService.instant('property.updateSuccessful'));
        },
        error => {
          this.showSnackbar(error.valueOf().error.message);
        });
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      panelClass: ['snackbar']
    });
  }

}
