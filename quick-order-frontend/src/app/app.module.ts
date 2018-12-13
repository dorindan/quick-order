import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {AppRoutingModule, routing} from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule, HttpClientXsrfModule} from "@angular/common/http";
import { HeaderComponent } from './header/header.component';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { TestComponent } from './test/test.component';
import { FooterComponent } from './footer/footer.component';
import { StartPageComponent } from './start-page/start-page.component';
import { StartLoggedInComponent } from './start-logged-in/start-logged-in.component';
import { ReservationComponent } from './reservation/reservation.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TestComponent,
    HeaderComponent,
    FooterComponent,
    StartPageComponent,
    StartLoggedInComponent,
    ReservationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'My-Xsrf-Cookie',
      headerName: 'My-Xsrf-Header',
    }),
    routing,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

