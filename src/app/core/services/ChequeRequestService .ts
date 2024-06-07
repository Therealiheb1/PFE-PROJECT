import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChequeRequestService {

  private chequeURL = 'http://localhost:8181/cheque'; 

  constructor(private http: HttpClient) { }

  createChequeRequest(): Observable<string> {
    return this.http.post<string>(`${this.chequeURL}/chequeReq`, {});
}
getChequeRequestStatus(): Observable<string> {
  return this.http.get(`${this.chequeURL}/chequeStatus`, { responseType: 'text' });
}
}
