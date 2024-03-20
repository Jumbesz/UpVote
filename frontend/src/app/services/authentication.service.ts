import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RegisterDataModel} from "../models/register-data.model";
import {BehaviorSubject, Observable} from "rxjs";
import {LoginDataModel} from "../models/login-data.model";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  baseUrl: string = "http://localhost:5432/api";

  private userSubject = new BehaviorSubject<LoginDataModel | null>(null);
  currentUser$: Observable<LoginDataModel | null> = this.userSubject.asObservable();
  isAdmin: boolean = false;

  constructor(private httpClient: HttpClient) {
  }

  setCurrentUser(user: LoginDataModel | null) {
    this.userSubject.next(user);
    this.isAdmin = user?.profileRole === 'ROLE_ADMIN';
  }

  storeCurrentUser(profile: LoginDataModel) {
    sessionStorage.setItem('currentUser', JSON.stringify(profile));
  }

  register(registerData: RegisterDataModel): Observable<RegisterDataModel> {
    return this.httpClient.post<RegisterDataModel>(`${this.baseUrl}/register`, registerData)
  }

  login(username: string, password: string): Observable<any> {
    const credentials = {username: username, password: password};
    return this.httpClient.post<any>(`${this.baseUrl}/login`, credentials);
  }

  logout() {
    sessionStorage.clear();
  }
}
