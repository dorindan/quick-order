import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-about-us',
  templateUrl: './about-us.component.html',
  styleUrls: ['./about-us.component.scss']
})
export class AboutUsComponent implements OnInit {

  hours = '10:00 - 20:00';

  constructor(private translateService: TranslateService) {
  }

  ngOnInit() {
  }


}
