import { Component, OnInit } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';


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





  constructor(private keycloakService: KeycloakService) {
   


  }
  private initializeUserOptions(): void {
    this.user = this.keycloakService.getUsername();
  }
  
  ngOnInit(): void {
    this.initializeUserOptions();
  }
  handlePageChange(e :any){
    // this.page = e;
    console.log(e);
    this.page = e;
  }
  saveSelectedList(e :any, obj : any){
    
    
    
  }

}





export interface UserData {
  id: string;
  name: string;
  progress: string;
  color: string;
}

