import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserInfo(): Observable<any> {
    return this.http.get<any>('http://localhost:8181/user/userT');
  }
  getUserName(): Observable<any> {
    return this.http.get<any>('http://localhost:8181/user/userN');
  }
}
