import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {Router} from '@angular/router';
import {User} from '../../models/User';
import {ApiService} from '../../services/api.service';
import {MatSnackBar} from '@angular/material';

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

  constructor(private apiService: ApiService, private router: Router, private snackBar: MatSnackBar) {
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
      const user = new User(this.userName, this.password);
      user.email = this.email;
      const url = 'api/users/signUp';
      this.apiService.postRequest(url, user).subscribe(rez => {
        this.router.navigate(['']);
        this.showSnackbar('Register successful.');
      }, error => {
        switch (error.status) {
          case 406: // not acceptable
            this.showSnackbar('UserName or Email are already taken. Please try again!');
            break;
          case 403: // forbidden exception
            this.showSnackbar('UserName has forbidden characters. Please try again!');
            break;
          case 400: // bad request exception
            this.showSnackbar('Something went bad. Please try again!');
            break;
          default:
            this.showSnackbar('Register failed. ');
        }
        return;
      });
    } else {
      this.showSnackbar('Complete all boxes with the appropriate data first!');
    }
  }

  private isValidMailFormat(email: string): boolean {
    let control: FormControl;
    control = new FormControl(email);
    const EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
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
