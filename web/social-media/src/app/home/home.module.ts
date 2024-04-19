import { NgModule } from '@angular/core';
import { CommonModule, NgComponentOutlet } from '@angular/common';
import { HomeComponent } from './home.component';
import { Route, RouterModule } from '@angular/router';
import { AuthGuard } from '../auth/keycloak-auth-guard';
import { OptionService } from './options/options.service';
import { UserService } from '../shared/user.service';
import { TableComponent } from '../shared/components/table/table.component';

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
    TableComponent,
    RouterModule.forChild([route])
  ],
  providers: [
    OptionService,
    UserService
  ]
})
export class HomeModule { }
