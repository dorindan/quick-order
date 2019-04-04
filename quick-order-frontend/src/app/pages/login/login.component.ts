import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {User} from '../../models/User';
import {LoginService} from '../../services/login.service';
import {TranslateService} from '@ngx-translate/core';
import {MatSnackBar} from '@angular/material/snack-bar';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};

  constructor(private translateService: TranslateService,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private loginService: LoginService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  login() {
      const user = new User(this.model.username, this.model.password);
      this.loginService.login(user).subscribe(rez => {
          sessionStorage.setItem(
            'token',
            rez.username
          );
        localStorage.setItem('defaultLanguage', rez.userAttributeDto.language);
        let language: string ;
        language = localStorage.getItem('defaultLanguage');
        this.translateService.setDefaultLang(language);
        this.switchLanguage(language);
          this.router.navigate(['loggedStart']);
      }, error1 => {
        this.showSnackbar("Username or password is incorrect. Please try again.");
      });
  }

  public switchLanguage(language: string) {
    this.translateService.use(language);
  }


}
