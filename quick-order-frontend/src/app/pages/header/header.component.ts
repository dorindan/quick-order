import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Attribute} from '../../models/Attribute';
import {LoginService} from '../../services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  private language = '';
  model: any = {};

  constructor(private translateService: TranslateService,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private loginService: LoginService) {

    if (localStorage.getItem('defaultLanguage') == null) {
      localStorage.setItem('defaultLanguage', 'en');
    }
    this.language = localStorage.getItem('defaultLanguage');

    translateService.setDefaultLang(this.language);
  }

  public switchLanguage(language: string) {
    this.language = language;
    this.translateService.use(this.language);
  }

  ngOnInit() {
  }

  public changeLanguage(language: string): void {
    this.language = language;
    this.switchLanguage(this.language);
    localStorage.setItem('defaultLanguage', this.language );
    // FIXME if(logged) and use userID to update language
    if (this.isAuthenticated()) {//
      this.updateLanguageOnUser(sessionStorage.getItem('token'), this.language);
    }
  }

  updateLanguageOnUser(userId: string, language: string) {
    const url = 'http://localhost:8080/api/users/' + userId + '/attributes';
    const attribute = new Attribute(language);
    this.http.post(url, attribute).subscribe();
  }

  isAuthenticated() {
    if (sessionStorage.getItem('token') === '') {
      return false;
    }
    return true;
  }

}
