import {Component, Input, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {User} from '../models/User';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Attribute} from '../models/Attribute';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  private language = '';
  model: any = {};

  constructor(private translate: TranslateService,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient ) {

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
    // FIXME if(logged) and use userID to update language
    if (true) {//
      this.updateLanguageOnUser(1, this.language);
    }
  }

  updateLanguageOnUser(userId: number, language: string) {
    const url = 'http://localhost:8080/users/' + userId + '/attributes';
    const attribute = new Attribute(language);
    this.http.post(url, attribute).subscribe();
  }

}
