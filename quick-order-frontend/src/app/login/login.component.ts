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
      let url = '/login';
      this.http.post<Observable<boolean>>(url, {
        username: this.model.username,
        password: this.model.password
      }).subscribe(isValid => {
        if (isValid) {
          console.log("am intrat 123");
          sessionStorage.setItem(
            'peroneu',
            btoa(this.model.username + ':' + this.model.password)
          );
          this.router.navigate(['test']);
        } else {
          alert("Authentication failed.");
        }
      });
      const token = this.tokenExtractor.getToken() as string;
      this.http.post<any>(url, {headers: new HttpHeaders().set('X-XSRF-TOKEN', token)})
  }



}
