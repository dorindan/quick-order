import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  private language = '';

  constructor(private translate: TranslateService,
              private loginService: LoginService) {

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

  public changeLanguage(language: string): void {
    this.language = language;
    this.switchLanguage(this.language);
    localStorage.setItem('defaultLanguage', this.language );
  }

  isAuthenticated()
  {
    if (sessionStorage.getItem('token') == '')
      return false;
    return true;
  }

}
