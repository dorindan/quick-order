import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  @Input() switchLanguage: Function;

  public language = '1' ;

  constructor() { }

  ngOnInit() {
  }

  public changeLanguage(): void {
    if (this.language === '1') {
      this.switchLanguage('en');
    } else if (this.language === '2') {
      this.switchLanguage('ro');
    } else {
      alert( this.language);
    }
  }

}
