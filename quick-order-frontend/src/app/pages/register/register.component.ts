import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {ApiService} from '../../services/api.service';
import {MatSnackBar} from '@angular/material';
import {AuthService} from '../../auth/auth.service';
import {SignUpInfo} from '../../auth/signup-info';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  public userName = '';
  public password = '';
  public email = '';
  public re_password = '';
  public termsAndConditions = false;
  hide = true;

  public rightPass = true;
  public rightRe_pass = true;
  public rightUsername = true;
  public rightEmail = true;
  public rightTermsAndConditions = true;
  public isActive = false;

  signupInfo: SignUpInfo;
  isSignedUp = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private translateService: TranslateService, private apiService: ApiService, private router: Router, private authService: AuthService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
  }

  showSnackbar(message: string) {
    this.snackBar.open(message, '', {
      duration: 3000,
      verticalPosition: 'top',
      panelClass: ['snackbar']
    });
  }

  public changePasswordVisibility() {
    this.isActive = !this.isActive;
  }

  public validateUsername(): void {
    const USER_REGEXP = /^[a-zA-Z0-9_.]{5,}$/i;
    let control: FormControl;
    control = new FormControl(this.userName);
    this.rightUsername = USER_REGEXP.test(control.value);
  }

  public validatePassword(): void {
    this.rightPass = this.password.length >= 6;
  }

  public validateRe_Password(): void {
    this.rightRe_pass = !(this.re_password.length < 6 || this.password.match(this.re_password) === null);
  }

  public validateEmail(): void {
    this.rightEmail = this.isValidMailFormat(this.email);
  }

  public validateTermsAndConditions(): void {
    this.rightTermsAndConditions = this.termsAndConditions;
  }

  public register() {
    if (this.checkIfAllIsValid()) {
      this.signupInfo = new SignUpInfo(
        this.userName,
        this.email,
        this.password);
      this.authService.signUp(this.signupInfo).subscribe(
        data => {
          this.router.navigate(['']);
          this.showSnackbar(this.translateService.instant('register.registerSuccessful'));
          this.isSignedUp = true;
          this.isSignUpFailed = false;
        },
        error => {
          this.errorMessage = error.error.message;
          this.isSignUpFailed = true;
          switch (error.status) {
            case 406: // not acceptable
              this.showSnackbar(this.translateService.instant('registerError.takenEmailOrUsername'));
              break;
            case 403: // forbidden exception
              this.showSnackbar(this.translateService.instant('registerError.forbiddenCharacters'));
              break;
            case 400: // bad request exception
              this.showSnackbar(this.translateService.instant('registerError.badRequest'));
              break;
            default:
              this.showSnackbar(this.translateService.instant('registerError.unknownError'));
          }
          return;
        }
      );
    } else {
      this.showSnackbar(this.translateService.instant('registerError.completeAll'));
    }
  }

  private isValidMailFormat(email: string): boolean {
    let control: FormControl;
    control = new FormControl(email);
    const EMAIL_REGEXP = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/i;
    return !(control.value === '' || (control.value.length <= 5 || !EMAIL_REGEXP.test(control.value)));
  }

  private checkIfAllIsValid(): boolean {
    if (this.password === this.re_password && this.password.length >= 6 &&
      this.userName.length >= 5 && this.rightUsername && this.isValidMailFormat(this.email) && this.termsAndConditions) {
      return true;
    } else {
      this.validateUsername();
      this.validatePassword();
      this.validateRe_Password();
      this.validateEmail();
      this.validateTermsAndConditions();
      return false;
    }
  }
}
