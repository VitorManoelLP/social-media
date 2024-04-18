import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { KeycloakWrapperService } from './auth/shared/keycloak.wrapper.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { KeycloakBearerInterceptor } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet
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

  constructor(private keycloakService: KeycloakWrapperService) {
    keycloakService.initKeycloak();
  }

}
