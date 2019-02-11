import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {AppRoutingModule, routing} from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoginComponent} from "./pages/login/login.component";
import {FooterComponent} from "./pages/footer/footer.component";
import {StartPageComponent} from "./pages/start-page/start-page.component";
import {StartLoggedInComponent} from "./pages/start-logged-in/start-logged-in.component";
import {ReservationComponent} from "./pages/reservation/reservation.component";
import {HeaderComponent} from "./pages/header/header.component";
import {LoginService} from "./services/login.service";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    StartPageComponent,
    StartLoggedInComponent,
    ReservationComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    routing,
    AppRoutingModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
