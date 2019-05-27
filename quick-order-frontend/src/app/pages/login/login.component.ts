import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {LoginService} from '../../services/login.service';
import {TranslateService} from '@ngx-translate/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthService} from "../../auth/auth.service";
import {TokenStorageService} from "../../auth/token-storage.service";
import {AuthLoginInfo} from "../../auth/login-info";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};
  hide = true;

  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;

  constructor(private translateService: TranslateService,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private loginService: LoginService,
              private snackBar: MatSnackBar,
              private authService: AuthService,
              private tokenStorage: TokenStorageService) {
  }

  ngOnInit() {
    sessionStorage.setItem('token', '');
    if (this.tokenStorage.getToken()) {
      this.tokenStorage.signOut();
    }
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  login() {
    this.loginInfo = new AuthLoginInfo(
      this.model.username,
      this.model.password);

    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        sessionStorage.setItem('token', data.username);
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.router.navigate(['loggedStart']);
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
        this.showSnackbar('Username or password is incorrect. Please try again.');
      }
    );
  }

  public switchLanguage(language: string) {
    this.translateService.use(language);
  }
}
