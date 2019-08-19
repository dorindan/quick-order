import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, Injector, NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {AppRoutingModule, routing} from './app-routing.module';
import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {LoginComponent} from './pages/login/login.component';
import {FooterComponent} from './pages/footer/footer.component';
import {StartPageComponent} from './pages/start-page/start-page.component';
import {StartLoggedInComponent} from './pages/start-logged-in/start-logged-in.component';
import {ReservationComponent} from './pages/reservation/reservation.component';
import {HeaderComponent} from './pages/header/header.component';
import {RegisterComponent} from './pages/register/register.component';
import {NgxPopper} from 'angular-popper';
import {MenuComponent} from './pages/menu/menu.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material.module';
import {MenuItemComponent} from './pages/menu-item/menu-item.component';
import {TableViewComponent} from './pages/table-view/table-view.component';
import {WaiterPageComponent} from './pages/waiter-page/waiter-page.component';
import {TableComponent} from './pages/table/table.component';
import {httpInterceptorProviders} from './auth/auth-interceptor';
import {FinishCommandComponent} from './pages/finish-command/finish-command.component';
import {ContactComponent} from './pages/contact/contact.component';
import {AgmCoreModule} from '@agm/core';
import {PropertyAdministrationComponent} from './pages/property-administration/property-administration.component';
import {ReservationLogComponent} from './pages/reservation-log/reservation-log.component';
import {AboutUsComponent} from './pages/about-us/about-us.component';
import { UserAdministrationComponent } from './pages/user-administration/user-administration.component';
import { StorageServiceModule} from 'angular-webstorage-service';
import {LOCATION_INITIALIZED} from "@angular/common";

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

export function appInitializerFactory(translate: TranslateService, injector: Injector) {
  return () => new Promise<any>((resolve: any) => {
    const locationInitialized = injector.get(LOCATION_INITIALIZED, Promise.resolve(null));
    locationInitialized.then(() => {
      const langToSet = 'EN'
      translate.use(langToSet).subscribe(() => {
        console.info(`Successfully initialized '${langToSet}' language.'`);
      }, err => {
        console.error(`Problem with '${langToSet}' language initialization.'`);
      }, () => {
        resolve(null);
      });
    });
  });
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
    TableViewComponent,
    MenuComponent,
    MenuItemComponent,
    WaiterPageComponent,
    TableComponent,
    ContactComponent,
    PropertyAdministrationComponent,
    FinishCommandComponent,
    ReservationLogComponent,
    AboutUsComponent,
    UserAdministrationComponent
  ],
  imports: [
    BrowserModule,
    StorageServiceModule,
    AppRoutingModule,
    NgbModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    routing,
    NgbModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyBMimoGCQC7mafNE7Ec9-iPWrVZxO0qx6Q'
    }),
    NgxPopper
  ],
  providers: [httpInterceptorProviders,
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializerFactory,
      deps: [TranslateService, Injector],
      multi: true
    }
    ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
