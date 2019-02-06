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
  public rightPass = false;
  public rightRe_pass = false;
  public rightUsername = false;
  public rightEmail = false;
  public termsAndConditions = false;

  public invalidFields = false;
  public isActive = false;

  constructor(  private route: ActivatedRoute,
                private router: Router,
                private http: HttpClient
               ) { }
  ngOnInit() {
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
    if (this.password.match(this.re_password) === null) {
      this.rightRe_pass = false;
    } else {
      this.rightRe_pass = true;
      if (this.invalidFields) {
        this.chackForinvalidFields();
      }
    }
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

  public changePasswordVisibility() {
    this.isActive = !this.isActive;
  }

  private chackForinvalidFields() {
    if (this.rightUsername && this.rightEmail && this.rightPass && this.rightRe_pass){
      this.invalidFields = false;
    } else {
      this.invalidFields = true;
    }
  }

  public register() {
    if (this.password === this.re_password && this.password.length >= 6 &&
      this.userName !== '' && this.rightEmail && this.termsAndConditions) {
      alert('I try to log in');
      let url = '';
      url = 'http://localhost:8080/signUp';
      this.http.get(url).subscribe((data: RegisterComponent) => {
        alert('am intrattttt!!!' + data);
      });
      alert('End of log in');
      return;
    } else if (this.userName === '') {
      alert('Enter the user name first.');
    } else if (!this.isValidMailFormat(this.email)) {
      alert('Enter a valid email address.');
    } else if (this.password.length < 6) {
      alert('The password dose not have at least 6 characters.');
    } else if (this.password !== this.re_password) {
      alert('Those passwords didn\'t match. Try again.');
    } else if (!this.termsAndConditions ){
      alert('You need to agree to the Terms & Privacy in order to create a account.');
    }
    this.chackForinvalidFields();
  }
}
