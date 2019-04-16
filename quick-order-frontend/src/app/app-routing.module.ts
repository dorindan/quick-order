import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {StartPageComponent} from './pages/start-page/start-page.component';
import {StartLoggedInComponent} from './pages/start-logged-in/start-logged-in.component';
import {RegisterComponent} from './pages/register/register.component';
import {MenuComponent} from './pages/menu/menu.component';
import {ReservationComponent} from './pages/reservation/reservation.component';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

const appRoutes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'startPage', component: StartPageComponent},
  {path: 'loggedStart', component: StartLoggedInComponent},
  {path: 'signUp', component: RegisterComponent},
  {path: 'menu', component: MenuComponent},
  {path: 'reserve', component: ReservationComponent},
  {path: '**', redirectTo: ''}
];

export const routing = RouterModule.forRoot(appRoutes);
