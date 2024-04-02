import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { users } from './utils/users';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private baseURL = "http://localhost:8181/user";

  constructor(private httpClient: HttpClient) { }
  
  getUsersList(): Observable<users[]>{
    return this.httpClient.get<users[]>(`${this.baseURL}`);
  }

  createusers(user: users): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`, users);
  }

  getuserById(id: number): Observable<users>{
    return this.httpClient.get<users>(`${this.baseURL}/${id}`);
  }

  updateUser(id: number, user: users): Observable<Object>{
    return this.httpClient.put(`${this.baseURL}/${id}`, user);
  }


}