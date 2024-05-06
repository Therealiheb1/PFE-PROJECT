import { Injectable } from '@angular/core';
import { Customers } from './core/models/auth.models';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustService {
  private apiUrl = 'http://localhost:8181/cust';

  constructor(private http: HttpClient) {}

  createCustomer(customer: any): Observable<any>  {
    const url = `${this.apiUrl}/customers`;
    return this.http.post<any>(url, customer);
  }
  updateCustomer(cin: number, customer: any): Observable<any> {
    const url = `${this.apiUrl}/update/${cin}`;
    return this.http.put<any>(url, customer);
  }
  getCustomersList(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
  
}