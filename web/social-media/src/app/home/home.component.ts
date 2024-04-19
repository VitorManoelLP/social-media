import { Component } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import Profile from '../auth/shared/context/profile';
import { OptionService } from './options/options.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent {

  readonly profile: KeycloakProfile = Profile.getInstance().user;

  constructor(public optionsService: OptionService) { }

  get optionComponent() {
    return this.optionsService.getCurrentOption()
  }

}
