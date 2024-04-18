import { Injectable } from "@angular/core";
import { KeycloakService } from "keycloak-angular";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class KeycloakWrapperService extends KeycloakService {

  constructor() {
    super();
  }

  public initKeycloak(): void {
    this.init({
      config: environment.authConfiguration,
      initOptions: {
        onLoad: 'login-required',
        checkLoginIframe: false
      }
    });
  }

}
