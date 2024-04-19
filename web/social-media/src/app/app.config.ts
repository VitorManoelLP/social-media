import { ApplicationConfig, Provider } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { KeycloakService } from 'keycloak-angular';
import keycloak from '../main';
import { KeycloakInterceptor } from './auth/shared/interceptor/keycloak.interceptor';

const keycloakInterceptorProvider: Provider = {
  provide: HTTP_INTERCEPTORS,
  useClass: KeycloakInterceptor,
  multi: true
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    {
      provide: KeycloakService,
      useExisting: keycloak
    },
    provideHttpClient(withInterceptorsFromDi()),
    keycloakInterceptorProvider
  ]
};
