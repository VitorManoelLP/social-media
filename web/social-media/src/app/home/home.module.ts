import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { Route, RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/keycloak-auth-guard';

const route: Route = {
  path: '',
  component: HomeComponent,
  canActivate: [AuthGuard]
};

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([route])
  ]
})
export class HomeModule { }
