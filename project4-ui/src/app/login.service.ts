import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

import { User } from './user';
import { FAKESER } from './mock-user';
import { MessageService } from './message.service';
import { AuthService, UserInfo } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(
    private http: HttpClient, 
    private messageService: MessageService,
    private authService: AuthService,
    private router: Router
  ) { }

  // getUser(): User {
  //   return FAKESER;
  // }

  private testUrl = 'http://localhost:8080/Project4/api/login';
  private loginUrl = 'http://localhost:8080/Project4/api/login';

  private testUser: User = {id: 1, username: 'admin1', password: 'pass', role: 1, group: null};

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };


  // //test get request
  // getUser(): Observable<User> {
  //   this.messageService.add('getting a fake user');
  //   //return of(FAKESER);
  //   //return this.http.get<User>(this.testUrl);
  //   return this.http.post<User>(this.testUrl, this.testUser, this.httpOptions);
  // }

  // //test post request
  // postUser(): Observable<User> {
  //   this.messageService.add('sending login request');
  //   //return of(FAKESER);
  //   //return this.http.get<User>(this.testUrl);
  //   return this.http.post<User>(this.testUrl, this.testUser, this.httpOptions);
  // }

  //logging in
  userLogin(loginForm: LoginForm): boolean {
    console.log("userLogin parameter:")
    console.log(loginForm);
    let loginSuccess: boolean;
    this.http.post<User>(this.loginUrl, loginForm, this.httpOptions).subscribe(
      data => {
        console.log("returned data: " + data.username);
        this.authService.setCurrentUser(data);
        if(data.id === 1){
          this.router.navigateByUrl("/new-question")
        }
        else{
          this.router.navigateByUrl("/user-quizzes");
        }
        return true;
      }, 
      err => {
        console.error(err.error);
        console.error("user not found");
        loginSuccess = false;
        return false;
      }
    );
    console.log("loginSuccess: " + loginSuccess);
    return loginSuccess;
  }

}

export interface LoginForm {
  username: string;
  password: string;
}