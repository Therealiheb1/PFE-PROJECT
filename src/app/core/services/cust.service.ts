import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customers, compte } from '../models/auth.models';


@Injectable({
  providedIn: 'root'
})
export class CustService {

    private baseURL = 'http://localhost:8181/cust/Customer';
    private baseURL1 = 'http://localhost:8181/cust';
    private baseURL2 = 'http://localhost:8181/cust/Compte';
    private baseURL3 = 'http://localhost:8181/compte';
    private chequeURL = 'http://localhost:8181/cheque';
    
    constructor(private httpClient: HttpClient) { }
    

  transfer(senderRib: string, receiverRib: string, value: number): Observable<string> {
    return this.httpClient.post<string>(`${this.baseURL}/${senderRib}/${receiverRib}/${value}`, null);
  }
  getUserAccounts(): Observable<compte[]> {
    return this.httpClient.get<compte[]>(`${this.baseURL3}/userAccounts`);
  }
  getAccountBalance(rib: string): Observable<number> {
    return this.httpClient.get<number>(`${this.baseURL3}/balance/${rib}`);
}

}


