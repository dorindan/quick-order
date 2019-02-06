import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  private language = '';

  constructor(private translate: TranslateService, ) {

    if (localStorage.getItem('defaultLanguage') == null) {
      localStorage.setItem('defaultLanguage', 'en');
    }
    this.language = localStorage.getItem('defaultLanguage');

    translate.setDefaultLang(this.language);
  }

  public switchLanguage(language: string) {
    this.language = language;
    this.translate.use(this.language);
  }

  ngOnInit() {
  }

  public changeLanguage(): void {
    if (this.language === 'en') {
      this.switchLanguage('en');
    } else if (this.language === 'ro') {
      this.switchLanguage('ro');
    } else {
      alert(this.language);
    }
    localStorage.setItem('defaultLanguage', this.language );
  }

}
