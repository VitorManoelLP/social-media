import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { HttpParam } from "./http.param";
import { Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class HttpClientWrapper {

  resource: string = '';
  location: string = '/social-media'

  constructor(private httpClient: HttpClient,) {
  }

  public get<T>(param: HttpParam, location: string = this.location): Observable<T> {
    return this.httpClient.get<T>(`${location}${param.buildRequest()}`);
  }


}
