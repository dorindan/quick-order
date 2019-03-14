import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import {AppRoutingModule, routing} from './app-routing.module';
import { AppComponent } from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoginComponent} from './pages/login/login.component';
import {FooterComponent} from './pages/footer/footer.component';
import {StartPageComponent} from './pages/start-page/start-page.component';
import {StartLoggedInComponent} from './pages/start-logged-in/start-logged-in.component';
import {ReservationComponent} from './pages/reservation/reservation.component';
import {HeaderComponent} from './pages/header/header.component';
import {RegisterComponent} from './pages/register/register.component';
import {NgxPopper} from 'angular-popper';
import { MenuComponent } from './pages/menu/menu.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';


export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    StartPageComponent,
    StartLoggedInComponent,
    ReservationComponent,
    RegisterComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    routing,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    routing,
    NgxPopper

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
