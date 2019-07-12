import {Component, OnInit} from '@angular/core';import {TranslateService} from '@ngx-translate/core';import {ActivatedRoute, Router} from '@angular/router';import {HttpClient} from '@angular/common/http';import {Attribute} from '../../models/Attribute';import {LoginService} from '../../services/login.service';import {User} from '../../models/User';import {TokenStorageService} from '../../auth/token-storage.service';@Component({  selector: 'app-header',  templateUrl: './header.component.html',  styleUrls: ['./header.component.scss']})export class HeaderComponent implements OnInit {  model: any = {};  private language = '';  private waiter: boolean;  constructor(private translateService: TranslateService,              private route: ActivatedRoute,              private router: Router,              private http: HttpClient,              private loginService: LoginService,              private tokenStorageService: TokenStorageService) {    if (localStorage.getItem('defaultLanguage') == null) {      localStorage.setItem('defaultLanguage', 'EN');    }    this.language = localStorage.getItem('defaultLanguage');    translateService.setDefaultLang(this.language);    this.switchLanguage(this.language);  }  public switchLanguage(language: string) {    this.language = language;    this.translateService.use(this.language);  }  ngOnInit() {  }  logout(): void {    this.tokenStorageService.signOut();  }  public changeLanguage(language: string): void {    this.language = language;    this.switchLanguage(this.language);    localStorage.setItem('defaultLanguage', this.language);    // FIXME if(logged) and use userID to update language    if (this.isAuthenticated()) {//      this.updateLanguageOnUser(sessionStorage.getItem('token'), this.language);    }  }  updateLanguageOnUser(userId: string, language: string) {    const url = 'http://localhost:8080/api/users/attributes';    const attribute = new Attribute(language);    let user;    user = new User(sessionStorage.getItem('token').toString(), '');    user.userAttributeDto = attribute;    this.http.post(url, user).subscribe();  }  isAuthenticated() {    return !(this.tokenStorageService.getAuthorities().length === 0);  }  isAuthenticatedUser() {    return this.tokenStorageService.isAuthenticatedWithRole('ROLE_USER');  }  isAuthenticatedWaiter() {    return this.tokenStorageService.isAuthenticatedWithRole('ROLE_WAITER');  }}