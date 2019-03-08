import { Component, OnInit, Input } from '@angular/core';
import {FormControl} from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpXsrfTokenExtractor, HttpClientModule } from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../../models/User';
import {ApiService} from '../../services/api.service';

@Injectable()
export class ConfigService {
  constructor(private http: HttpClient) { }
}

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

  public rightPass = true;
  public rightRe_pass = true;
  public rightUsername = true;
  public rightEmail = true;
  public rightTermsAndConditions = true;
  public isActive = false;

  constructor(private apiService: ApiService, private router: Router) { }
  ngOnInit() {
  }

  public changePasswordVisibility() {
    this.isActive = !this.isActive;
  }

  private isValidMailFormat(email: string): boolean {
    let control: FormControl;
    control = new FormControl(email);
    const EMAIL_REGEXP = /^[a-z0-9!#$%&'*+\/=?^_`{|}~.-]+@[a-z0-9]([a-z0-9-]*[a-z0-9])?(\.[a-z0-9]([a-z0-9-]*[a-z0-9])?)*$/i;
    if (control.value === '' || (control.value.length <= 5 || !EMAIL_REGEXP.test(control.value))) {
      return false;
    }
    return true;
  }

  public validateUsername(): void {
    if (this.userName.length < 5) {
      this.rightUsername = false;
    } else {
      this.rightUsername = true;
    }
  }
  public validatePassword(): void {
    if (this.password.length >= 6) {
      this.rightPass = true;
    } else {
      this.rightPass = false;
    }
  }
  public validateRe_Password(): void {
    if (this.re_password.length < 6 || this.password.match(this.re_password) === null ) {
      this.rightRe_pass = false;
    } else {
      this.rightRe_pass = true;
    }
  }
  public validateEmail(): void {
    if (this.isValidMailFormat(this.email)) {
      this.rightEmail = true;
    } else {
      this.rightEmail = false;
    }
  }

  public validateTermsAndConditions(): void {
    this.rightTermsAndConditions = this.termsAndConditions;
  }

  private checkIfAllIsValid(): boolean {
    if (this.password === this.re_password && this.password.length >= 6 &&
      this.userName.length > 5 && this.isValidMailFormat(this.email) && this.termsAndConditions) {
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

  public register() {
     if (this.checkIfAllIsValid()) {
      alert('I try to log in');
      const user = new User(this.userName, this.password);
      user.email = this.email;
      const url = 'api/users/signUp';
      this.apiService.postRequest(url, user).subscribe(rez => {
        this.router.navigate(['']);
      }, error1 => {
        alert('Register failed.');
      });
     }
      return;
  }
}
