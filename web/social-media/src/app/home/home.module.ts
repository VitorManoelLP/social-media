import { NgModule } from '@angular/core';
import { CommonModule, NgComponentOutlet } from '@angular/common';
import { HomeComponent } from './home.component';
import { Route, RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/keycloak-auth-guard';
import { UserService } from '../shared/user.service';
import { TableCustomModule } from '../shared/components/table/table.module';

const route: Route = {
  path: '',
  component: HomeComponent,
  canActivate: [AuthGuard]
};

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    NgComponentOutlet,
    TableCustomModule,
    RouterModule.forChild([route])
  ],
  providers: [
    UserService
  ]
})
export class HomeModule { }
