import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpXsrfTokenExtractor} from "@angular/common/http";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient,
              private tokenExtractor: HttpXsrfTokenExtractor) { }
              

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  login() {
    console.log("am intrat maaaaaaaaai");
      let url = 'http://localhost:8080/login';
      this.http.post<Observable<boolean>>(url, {
        username: this.model.username,
        password: this.model.password
      }).subscribe(isValid => {
        if (isValid) {
          console.log("am intrat 123");
          sessionStorage.setItem(
            'token',
            btoa(this.model.username + ':' + this.model.password)
          );
          this.router.navigate(['test']);
        } else {
          alert("Authentication failed.");
        }
      });
  }



}
