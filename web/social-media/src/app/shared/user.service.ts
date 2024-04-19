import { Observable } from "rxjs";
import { HttpClientWrapper } from "./http/http-client.service";
import { HttpParam, PageParameter, Pageable } from "./http/http.param";
import User from "../model/user.model";
import { Injectable } from "@angular/core";
import keycloak from "../../main";

@Injectable()
export class UserService {

  constructor(private httpClient: HttpClientWrapper) {
    httpClient.resource = '/api/users';
  }

  public getUsers(search: string, page: PageParameter): Observable<Pageable<User>> {
    return this.httpClient.get(HttpParam.of({
      resource: this.httpClient.resource,
      page: page,
      search: search
    }));
  }

}
