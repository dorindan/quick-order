import { Component, OnInit, Input } from '@angular/core';
import {FormControl} from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpXsrfTokenExtractor, HttpClientModule } from '@angular/common/http';
import {Observable} from 'rxjs';

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

  public invalidFields = false;
  public isActive = false;

  constructor(  private route: ActivatedRoute,
                private router: Router,
                private http: HttpClient
               ) { }
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
      if (this.invalidFields) {
        this.chackForinvalidFields();
      }
    }
  }
  public validatePassword(): void {
    if (this.password.length >= 6) {
      this.rightPass = true;
      if (this.invalidFields) {
        this.chackForinvalidFields();
      }
    } else {
      this.rightPass = false;
    }
  }
  public validateRe_Password(): void {
    if (this.re_password.match('') || this.password.match(this.re_password) === null ) {
      this.rightRe_pass = false;
    } else {
      this.rightRe_pass = true;
      if (this.invalidFields) {
        this.chackForinvalidFields();
      }
    }
  }
  public validateEmail(): void {
    if (this.isValidMailFormat(this.email)) {
      this.rightEmail = true;
      if (this.invalidFields) {
        this.chackForinvalidFields();
      }
    } else {
      this.rightEmail = false;
    }
  }

  public validateTermsAndConditions(): void {
    this.rightTermsAndConditions = this.termsAndConditions;
  }

  private chackForinvalidFields() {
    if (this.rightUsername && this.rightEmail && this.rightPass && this.rightRe_pass) {
      this.invalidFields = false;
    } else {
      this.invalidFields = true;
    }
  }

  private checkIfAllIsValid(): boolean {
    if (this.password === this.re_password && this.password.length >= 6 &&
      this.userName.length < 5 && this.isValidMailFormat(this.email) && this.termsAndConditions) {
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
      let url = '';
      url = 'http://localhost:8080/signUp';
      this.http.get(url).subscribe((data: RegisterComponent) => {
        alert('am intrattttt!!!' + data);
      });
    }

      this.chackForinvalidFields();
      return;
  }
}
