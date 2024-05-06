import { Component, OnInit } from '@angular/core';
import { Customers } from '../core/models/auth.models';
import { CustService } from '../core/services/cust.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustDetailsComponent } from '../cust-details/cust-details.component';
@Component({
  selector: 'app-admin-pannel',
  templateUrl: './admin-pannel.component.html',
  styleUrls: ['./admin-pannel.component.scss']
})
export class AdminPannelComponent implements OnInit {

  cust: Customers[];

constructor(private modalService: NgbModal,
  private custservice: CustService,
    private router: Router) { }




 


  pages:number;
  page = 1;
  pageSize = 5;





  
  

  
  ngOnInit(): void {
    this.getCustomers();
  }
  private getCustomers(){
    this.custservice.getCustomersList().subscribe(data => {
      this.cust = data;
    });
  }
  custdetails(cin: number) {
    this.router.navigate(['custdetails', cin]);
  }
  handlePageChange(e :any){
    // this.page = e;
    console.log(e);
    this.page = e;
  }
  saveSelectedList(e :any, obj : any){
    
    
    
  }
  openDetailsModal(customer: Customers) {
    const modalRef = this.modalService.open(CustDetailsComponent, { size: 'lg' }); 
    modalRef.componentInstance.cust = customer;
  }
}





export interface UserData {
  id: string;
  name: string;
  progress: string;
  color: string;
}

