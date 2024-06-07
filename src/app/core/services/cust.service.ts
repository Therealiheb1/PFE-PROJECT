    import { Injectable } from '@angular/core';
    import { HttpClient, HttpParams } from '@angular/common/http';
    import { Observable } from 'rxjs';
    import { Customers, Pages, compte } from '../models/auth.models';
  import { Page } from 'ngx-pagination';


    @Injectable({
      providedIn: 'root'
    })
    export class CustService {

        private baseURL = 'http://localhost:8181/cust/Customer';
        private baseURL1 = 'http://localhost:8181/cust';
        private baseURL2 = 'http://localhost:8181/cust/Compte';
        private baseURL21 = 'http://localhost:8181/cust/addCompte';
        private baseURL3 = 'http://localhost:8181/compte';
        private userURL = 'http://localhost:8181/user';
        private chequeUrl = 'http://localhost:8181/cheque';
        constructor(private httpClient: HttpClient) { }
        
        getCustomersList(page: number, size: number): Observable<Customers[]> {
        
          return this.httpClient.get<Customers[]>(`${this.baseURL}/${page}/${size}`);
        }
      
    
        createUser(userDTO: any): Observable<any> {
          return this.httpClient.post<any>(`${this.userURL}/add`, userDTO);
        }
      
      
        getCustomersByCin(id: number): Observable<Customers>{
          return this.httpClient.get<Customers>(`${this.baseURL}/${id}`);
        }
      
        updateCustomers(id: number, Customers: Customers): Observable<Object>{
          return this.httpClient.put(`${this.baseURL}/${id}`, Customers);
        }
        getComptesList(id: number, page: number, size: number, status: boolean): Observable<compte[]> {
          return this.httpClient.get<compte[]>(`${this.baseURL}/${id}/${page}/${size}/${status}`);
        }

        desaOrActivateAccountByRib(rib: string): Observable<string> {
          return this.httpClient.put<string>(`${this.baseURL2}/${rib}`, null);
        }
        addCompte(cin: string): Observable<compte> {
          return this.httpClient.post<compte>(`${this.baseURL21}/${cin}`, null);
        }

        filterCustomers(params: any, page: number, size: number): Observable<any> {
          const url = `${this.baseURL1}/filter/${page}/${size}`;
          return this.httpClient.post<any>(url, params);
        }

      //   filterCustomers(firstName: string, lastName: string, cin: string, sexe: string, tel: string, email: string, agence: string, profession: string): Observable<Customers[]> {
      //     const params = new HttpParams()
      //         .set('firstName', firstName)
      //         .set('lastName', lastName)
      //         .set('cin', cin)
      //         .set('sexe', sexe)
      //         .set('tel', tel)
      //         .set('email', email)
      //         .set('agence', agence)
      //         .set('profession', profession)

      
      //     return this.httpClient.get<Customers[]>('http://localhost:8181/cust/filter', { params });
      // }
      filterComptes(rib: string, iban: string, solde: number): Observable<compte[]> {
        const params = new HttpParams()
          .set('rib', rib)
          .set('iban', iban)
          .set('solde', solde);
    
        return this.httpClient.get<compte[]>('http://localhost:8181/compte/filterCbank', { params });
        
      }
      filterCustomersByKeyword(keyword: string): Observable<Customers[]> {
        const params = new HttpParams().set('keyword', keyword);
        return this.httpClient.get<Customers[]>('http://localhost:8181/cust/filter/' + keyword, { params });
      }
      transfer(senderRib: string, receiverRib: string, value: number): Observable<string> {
        return this.httpClient.post<string>(`${this.baseURL}/${senderRib}/${receiverRib}/${value}`, null);
      }
      // filterCompteForCustomer(id: number, rib: string, iban: string, solde: number): Observable<compte[]> {
      //   let params = new HttpParams()
      //   .set('rib', rib)
      //   .set('iban', iban)
      //   .set('solde', solde);
    
      //   return this.httpClient.get<compte[]>(`${this.baseURL3}/filterCompte/${id}`, { params });
      // }

    
      changeAccountType(rib: string, newTypeId: string): Observable<string> {
        return this.httpClient.put<string>(`${this.baseURL}/Compte/changeType/${rib}/${newTypeId}`, null);
      }
      
      addSolde(rib: string, amount: number): Observable<any> {
        return this.httpClient.put<any>(`${this.baseURL1}/addSolde/${rib}/${amount}`, null);
      }
    
    
      filterCompteForCustomer(customerId: number, status: boolean, rib?: string, iban?: string, solde?: number): Observable<compte[]> {
        let params = new HttpParams();
        if (rib) params = params.set('rib', rib);
        if (iban) params = params.set('iban', iban);
        if (solde !== undefined) params = params.set('solde', solde.toString());
    
        return this.httpClient.get<compte[]>(`${this.baseURL3}/filterCompte/${customerId}/${status}`, { params });
      }
      getChequeRequestsByRole(): Observable<any[]> {
        return this.httpClient.get<any[]>(`${this.chequeUrl}/requests`);
      }
      updateCurrentStep(CustomerId: number): Observable<string> {
        console.log("Custt               :"+CustomerId)
        return this.httpClient.put<string>(`${this.chequeUrl}/updateCurrentStep/${CustomerId}`, null);
      }
    }


