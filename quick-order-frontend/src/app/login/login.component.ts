import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/User';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  model: any = {};

  constructor(private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) { }

  ngOnInit() {
    sessionStorage.setItem('token', '');
  }

  login() {
      const url = 'http://localhost:8080/login';
      const user = new User(this.model.username, this.model.password);
      this.http.post<Observable<boolean>>(url, user).subscribe(isValid => {
        if (isValid) {
          sessionStorage.setItem(
            'token',
            btoa(this.model.username + ':' + this.model.password)
          );
          this.router.navigate(['loggedStart']);
        } else {
          alert('Authentication failed.');
        }
      });
  }



}
