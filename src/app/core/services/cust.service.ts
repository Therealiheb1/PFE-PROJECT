import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customers } from '../models/auth.models';


@Injectable({
  providedIn: 'root'
})
export class CustService {

    private baseURL = 'http://localhost:8181/cust/customers';
  
    constructor(private httpClient: HttpClient) { }
    
    getCustomersList(): Observable<Customers[]>{
      return this.httpClient.get<Customers[]>(`${this.baseURL}`);
    }
  
    createCustomers(Customers: Customers): Observable<Object>{
            return this.httpClient.post<Customers>('http://localhost:8181/cust/Addcustomers', Customers);
          }
  
    getCustomersByCin(id: number): Observable<Customers>{
      return this.httpClient.get<Customers>(`${this.baseURL}/${id}`);
    }
  
    updateCustomers(id: number, Customers: Customers): Observable<Object>{
      return this.httpClient.put(`${this.baseURL}/${id}`, Customers);
    }
  }