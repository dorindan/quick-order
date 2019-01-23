import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {



  public language = '1' ;

  constructor(private translate: TranslateService) {
    translate.setDefaultLang('en');
  }

  public switchLanguage(language: string) {
    this.translate.use(language);
  }
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
