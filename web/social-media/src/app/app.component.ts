import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { KeycloakAngularModule, KeycloakBearerInterceptor } from 'keycloak-angular';
import { CommonModule } from '@angular/common';
import { KeycloakProfile } from 'keycloak-js';
import Profile from './auth/shared/profile';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    RouterModule,
    KeycloakAngularModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakBearerInterceptor,
      multi: true
    }
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  user: KeycloakProfile = Profile.getInstance().user;

}
