import { Component, OnInit } from '@angular/core';
import {PropertyService} from "../../services/property.service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {

  location: string;
  email: string;
  lat: number;
  lng: number;
  openHour: string;
  closeHour: string;
  constructor(private propertyService: PropertyService) { }

  ngOnInit() {
    this.propertyService.getBistroProperty().subscribe(response => {
      const startHours: string[] = response.startProgramTime.split(':', 3);
      this.openHour = +startHours[0] + ":" + startHours[1];
      const endHours: string[] = response.endProgramTime.split(':', 3);
      this.closeHour = +endHours[0] + ":" + endHours[1];
      this.email = response.email;
      this.lat = response.latitude;
      this.lng = response.longitude;
      this.location = response.streetName;
    });
  }

}
