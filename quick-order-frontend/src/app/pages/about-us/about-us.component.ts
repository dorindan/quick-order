import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {PropertyService} from '../../services/property.service';

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.scss']
})
export class AboutUsComponent implements OnInit {

  hours = '';

  constructor(private translateService: TranslateService,
              private propertyService: PropertyService) {
    this.propertyService.getBistroProperty().subscribe(response => {
      const startHours: string[] = response.startProgramTime.split(':', 3);
      const startHour = new Date();
      startHour.setHours(+startHours[0], +startHours[1], +startHours[2]);
      const endHours: string[] = response.endProgramTime.split(':', 3);
      const endHour = new Date();
      endHour.setHours(+endHours[0], +endHours[1], +endHours[2]);
      this.hours = startHour.getHours() + ':00' + ' - '
        + endHour.getHours() + ':00';
    });
  }

  ngOnInit() {
  }


}
