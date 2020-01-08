import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser$: BehaviorSubject<UserInfo> = new BehaviorSubject<UserInfo>(undefined);
  
  setCurrentUser(userInfo: UserInfo): void {
    console.log(userInfo);
    this.currentUser$.next(userInfo);
  }

  getCurrentUser(): Observable<UserInfo> {
    return this.currentUser$.asObservable();
  }

  signOut() {
    this.currentUser$.next(undefined);
  }

  constructor() { }
}

export interface UserInfo {
  id: number;
  username: string;
  role: number;
  group: number;
}