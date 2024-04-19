import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { KeycloakAuthGuard } from "keycloak-angular";
import keycloak from "../../main";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard extends KeycloakAuthGuard {

  constructor(protected override readonly router: Router) {
    super(router, keycloak);
  }

  override async isAccessAllowed(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!this.authenticated) {
      await keycloak.login({
        redirectUri: window.location.origin + state.url
      });
    }

    return this.authenticated;
  }

}
