import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class chartsService {

  constructor(private http: HttpClient) { }

  getCountOperationsThisMonth(): Observable<number> {
    return this.http.get<number>('http://localhost:8181/compte/countOperationsThisMonth');
  }

  getCountAllAccounts(): Observable<number> {
    return this.http.get<number>('http://localhost:8181/compte/countAllAccounts');
  }

  getCountEpargneAccounts(): Observable<number> {
    return this.http.get<number>('http://localhost:8181/compte/countEpargneAccounts');
  }

  getCountCourantAccounts(): Observable<number> {
    return this.http.get<number>('http://localhost:8181/compte/countCourantAccounts');
  }
}