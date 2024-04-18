import { Injectable } from "@angular/core";
import { KeycloakProfile } from "keycloak-js";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private _loggedUser!: KeycloakProfile;

  public get user(): KeycloakProfile {
    return this._loggedUser;
  }

  public set loggedUser(user: KeycloakProfile) {
    this._loggedUser = user;
  }

}
