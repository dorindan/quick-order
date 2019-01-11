import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {StartPageComponent} from './start-page/start-page.component';
import {LoginComponent} from './login/login.component';
import {StartLoggedInComponent} from './start-logged-in/start-logged-in.component';
import {RegisterComponent} from './register/register.component';

const routes: Routes = [
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }


const appRoutes: Routes = [
  { path: '', component: StartPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'startPage', component: StartPageComponent },
  { path: 'loggedStart', component: StartLoggedInComponent},
  { path: 'register', component: RegisterComponent},
  { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
