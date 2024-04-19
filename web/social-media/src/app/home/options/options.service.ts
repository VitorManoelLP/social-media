import { Injectable } from "@angular/core";
import { FriendsComponent } from "../friends/friends.component";
import { UserService } from "../../shared/user.service";
import { MeComponent } from "../me/me.component";

@Injectable()
export class OptionService {

  readonly options: Option = {
    me: { active: true },
    friends: { active: false }
  };

  constructor(private userService: UserService) {
  }

  getCurrentOption() {

    const optionSelected = Object.keys(this.options).filter(key => this.options[key].active);

    if (!optionSelected.length) {
      return {};
    }

    const optionKey = optionSelected[0];
    return this.optionsComponents[optionKey];
  }

  get optionsComponents(): { [key: string]: any } {
    return {
      me: {
        component: MeComponent,
        inputs: { userService: this.userService }
      },
      friends: {
        component: FriendsComponent,
        inputs: { userService: this.userService }
      }
    }
  }

  onSwitchOption(id: string) {
    Object.keys(this.options).forEach(key => this.options[key].active = false)
    this.options[id].active = true;
  }

}

interface Option {
  [key: string]: { active: boolean };
}
