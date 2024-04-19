import { Component, Input } from '@angular/core';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html'
})
export class MeComponent {

  @Input({ required: true })
  userService!: UserService;

}
