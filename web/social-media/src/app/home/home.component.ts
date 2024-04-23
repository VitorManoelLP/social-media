import { Component } from '@angular/core';
import { KeycloakProfile } from 'keycloak-js';
import Profile from '../auth/shared/context/profile';
import { UserService } from '../shared/user.service';
import { PageParameter } from '../shared/http/http.param';
import UserFound from '../model/user-found.model';
import User from '../model/user.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent {

  public options: { [key: string]: any } = {
    me: {
      active: true
    },
    friends: {
      active: false
    }
  };

  optionSelected: { [key: string]: any } = this.options['me'];

  readonly profile: KeycloakProfile = Profile.getInstance().user;

  constructor(public userService: UserService) { }

  onSwitchOption(option: string) {
    Object.keys(this.options).forEach(option => this.options[option].active = false);
    this.options[option].active = true;
    this.optionSelected = this.options[option];
  }

  callGetUser() {
    return (search: string, page: PageParameter) => this.userService.getUsers(search, page);
  }

  buildSearch(value: string) {
    return `firstName=="${value}*",username=="${value}*"`;
  }

  onClickAdd(value: UserFound) {
    if (value.alreadyRequested) return;
    value['icon'] = 'fa fa-check';
    value.alreadyRequested = true;
    this.userService.sendRequest(value['id']).subscribe();
  }

  defineAlreadySentRequest(value: UserFound) {
    if (value.alreadyRequested) {
      value['icon'] = 'fa fa-check';
    }
  }

  shouldHiddenRequest(value: UserFound) {
    return !value.hasRequest;
  }

  shouldShowAcceptAction(value: UserFound) {
    return value.hasRequest;
  }

}
