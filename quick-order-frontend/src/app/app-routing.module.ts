import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './pages/login/login.component';
import {StartPageComponent} from './pages/start-page/start-page.component';
import {StartLoggedInComponent} from './pages/start-logged-in/start-logged-in.component';
import {RegisterComponent} from './register/register.component';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
const appRoutes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'login', component: LoginComponent },
  { path: 'startPage', component: StartPageComponent },
  { path: 'loggedStart', component: StartLoggedInComponent},
  { path: 'signUp', component: RegisterComponent},
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
