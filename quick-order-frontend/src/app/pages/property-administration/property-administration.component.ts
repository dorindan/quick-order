import {Component, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {PropertyService} from "../../services/property.service";
import {Property} from "../../models/Property";
import {MatSnackBar} from "@angular/material";
import * as moment from 'moment'

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

  constructor(private snackBar: MatSnackBar,
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

  private isValidMailFormat(email: string): boolean {
    let control: FormControl;
    control = new FormControl(email);
    const EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
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
      this.showSnackbar("longitude must be of form a.b");
    }
    else if (!this.isFloat(this.locationLatitude)) {
      this.showSnackbar("latitude must be of form a.b");
    }
    else if (!this.rightEmail) {
      this.showSnackbar("email is not valid");
    }
    else if (!this.rightStartTime) {
      this.showSnackbar("Start time is not valid");
    }
    else if (!this.rightEndTime) {
      this.showSnackbar("End time is not valid");
    }
    else this.propertyService.setBistroProperty(property).subscribe(response => {
          this.showSnackbar("Property updated");
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
