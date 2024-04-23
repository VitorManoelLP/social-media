import { Observable } from "rxjs";
import { HttpClientWrapper } from "./http/http-client.service";
import { HttpParam, PageParameter, Pageable } from "./http/http.param";
import { Injectable } from "@angular/core";
import UserFound from "../model/user-found.model";

@Injectable()
export class UserService {

  constructor(private httpClient: HttpClientWrapper) {
    httpClient.resource = '/api/users';
  }

  public getUsers(search: string, page: PageParameter): Observable<Pageable<UserFound>> {
    return this.httpClient.get(HttpParam.of({
      resource: this.httpClient.resource,
      page: page,
      search: search
    }));
  }

  public sendRequest(idUser: string): Observable<void> {
    return this.httpClient.post(HttpParam.of({
      resource: `${this.httpClient.resource}/request/${idUser}`
    }));
  }

}
