import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { User } from '../user';
import { FAKESER } from '../mock-user';
import { LoginService, LoginForm } from '../login.service';
import { AuthService, UserInfo } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  fakeUser: User = {id: 2, username: 'lol you suck', password: 'pass', role: 1, group: 0};
  testUser: User = {id: 1, username: 'admin1', password: 'pass', role: 1, group: 0};

  currentUser$: Observable<UserInfo> = this.authService.getCurrentUser();

  username: string;
  password: string;

  loginForm: LoginForm = {
    username: '',
    password: ''
  };

  loginSuccess: boolean;

  // getFake(): void {
  //   this.loginService.getUser()
  //     .subscribe(fakeUser => this.fakeUser = fakeUser);
  // }

  // getCurrent() {
  //   this.authService.getCurrentUser().subscribe(current => this.currentUser = current);
  // }

  postUser(): void {
    
  }

  constructor(private loginService: LoginService, private authService: AuthService) { }

  ngOnInit() {
  }

  sendLogin(){
    this.loginSuccess = this.loginService.userLogin({username: this.username, password: this.password});
    //this.loginService.userLogin(this.testUser);
    //this.getCurrent();
    console.log(`send login result: ${this.loginSuccess}`);
    console.log(this.currentUser$);
  }

}
