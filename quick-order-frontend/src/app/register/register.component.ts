import { Component, OnInit, Input } from '@angular/core';
import {FormControl} from '@angular/forms';

import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpXsrfTokenExtractor} from '@angular/common/http';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})

export class RegisterComponent implements OnInit {

  @Input() switchLanguage: Function;

  public userName = '';
  public password = '';
  public email = '';
  public re_password = '';
  public rightPass = true;
  public rightRe_pass = true;
  public isActive = true;

  constructor(  private route: ActivatedRoute,
                private router: Router,
                private http: HttpClient
               ) { }
  ngOnInit() {
    // sessionStorage.setItem('token', '');
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
    } else {
      this.rightPass = false;
    }
  }

  public validateRe_Password(): void {
    if (this.password !== this.re_password) {
      this.rightRe_pass = false;
    } else {
      this.rightRe_pass = true;
    }
  }

  public cancle() {
    alert('Back to LogIn page.');
   // this.router.navigate(['home']);
  }


  public register() {
    if (this.password === this.re_password && this.password.length >= 6 && this.userName !== '' && this.isValidMailFormat(this.email) ) {
      alert('I try to log in');
      let url = '';
      url = 'http://localhost:8080/signUp';
      this.http.post<Observable<boolean>>(url, {
        username: this.userName,
        password: this.password,
        email: this.email
      }).subscribe(isValid => {
        alert('Am luat informatia');
        if (isValid) {
          alert('Done');
          // sessionStorage.setItem(
          //   'token',
          //   btoa(this.model.username + ':' + this.model.password)
          // );
          // this.router.navigate(['test']);
        } else {
          alert('Register faild');
        }
        alert(isValid);
      });
      alert('End of log in');
    } else if (this.userName === '') {
      alert('Enter the user name first.');
    } else if (!this.isValidMailFormat(this.email)) {
      alert('Enter a valid email address.');
    } else if (this.password.length < 6) {
      alert('The password dose not have at least 6 characters.');
    } else if (this.password !== this.re_password) {
      alert('Those passwords didn\'t match. Try again.');
    }
  }


//   this.http.post<Observable<boolean>>(url, {
//   userName: this.model.username,
//   password: this.model.password
// }).subscribe(isValid => {
//   if (isValid) {
//     sessionStorage.setItem(
//       'token',
//       btoa(this.model.username + ':' + this.model.password)
//     );
//     this.router.navigate(['']);
//   } else {
//     alert("Authentication failed.")
//   }
// });

  // reg() {
  //   let url = 'http://localhost:8080/login';
  //   this.http.post<Observable<boolean>>(url, {
  //     username: this.model.username,
  //     password: this.model.password
  //   }).subscribe(isValid => {
  //     if (isValid) {
  //       sessionStorage.setItem(
  //         'token',
  //         btoa(this.model.username + ':' + this.model.password)
  //       );
  //       this.router.navigate(['home']);
  //     } else {
  //       alert("Authentication failed.");
  //     }
  //   });
  // }
}
