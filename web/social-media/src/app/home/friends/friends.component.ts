import { Component, Input, OnInit } from '@angular/core';
import { UserService } from '../../shared/user.service';
import { PageParameter, Pageable } from '../../shared/http/http.param';
import { tap } from 'rxjs';
import User from '../../model/user.model';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html'
})
export class FriendsComponent implements OnInit {

  loading: boolean = false;
  users: User[] = [];

  @Input({ required: true })
  userService!: UserService;

  ngOnInit(): void {
    this.findAllUsers('', { page: 0, size: 10 });
  }

  public findAllUsers(search: string, page: PageParameter) {
    this.loading = true;
    this.userService.getUsers(search, page).pipe(tap(() => this.loading = false)).subscribe(content => this.users = content.content);
  }

  onChangePage(page: any) {

  }

}
