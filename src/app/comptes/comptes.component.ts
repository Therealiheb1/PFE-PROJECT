import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { compte } from '../core/models/auth.models';
import { CustService } from '../core/services/cust.service';
import { Console } from 'console';
import { Router } from '@angular/router';



@Component({
  selector: 'app-comptes',
  templateUrl: './comptes.component.html',
  styleUrls: ['./comptes.component.scss']
})

	
export class ComptesComponent implements OnInit {

  user = '';
  usersList = [];


  pages:number;
  page = 1;
  pageSize = 5;
  userAccounts: compte[] = [];




  constructor(private router: Router ,private keycloakService: KeycloakService,private CustService:CustService) {
  }
  private initializeUserOptions(): void {
    this.user = this.keycloakService.getUsername();
  }
  
  ngOnInit(): void {
    this.initializeUserOptions();
    this.getUserAccounts();
  }
  handlePageChange(e :any){
    // this.page = e;
    console.log(e);
    this.page = e;
  }

  navigateToDefault(rib: string): void {
    this.router.navigate(['/default'], { queryParams: { rib: rib } });
  }
  getUserAccounts(): void {
    this.CustService.getUserAccounts().subscribe(accounts => {
      this.userAccounts = accounts;
      
    });
    console.log('doneeee !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!');
  }

}
console.log('**************************************************************************************')
console.log(window.location.href)
console.log('**************************************************************************************')



export interface UserData {
  id: string;
  name: string;
  progress: string;
  color: string;
}

