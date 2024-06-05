import { Component, OnInit } from '@angular/core';
import { Customers } from '../core/models/auth.models';
import { CustService } from '../core/services/cust.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustDetailsComponent } from '../cust-details/cust-details.component';
import { CpannelComponent } from '../cpannel/cpannel.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-pannel',
  templateUrl: './admin-pannel.component.html',
  styleUrls: ['./admin-pannel.component.scss']
})
export class AdminPannelComponent implements OnInit {
  filteredCustomers: Customers[];
  searchQuery: string = '';
  cust: Customers[];
  paginationandsort = {
    pageSize: 5
  };
  filters = {
    firstName: '',
    lastName: '',
    email: '',
    cin: '',
    tel: '',
    sexe: '',
    profession: '',
    agence: ''
  };

  constructor(private modalService: NgbModal, private custservice: CustService, private router: Router) {}

  page: number = 0;
  pageSize: number[] = [1, 3, 5, 10, 20, 30];

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomersSpageSize(pageSize: number) {
    console.log('getCustomersSpageSize called with pageSize:', pageSize);
    this.paginationandsort.pageSize = pageSize;
    this.custservice.getCustomersList(this.page, pageSize).subscribe(data => {
      this.cust = data;
      this.applyFilter();
    });
  }

  getCustomers() {
    console.log('getCustomers called with page:', this.page, 'and pageSize:', this.paginationandsort.pageSize);
    this.custservice.getCustomersList(this.page, this.paginationandsort.pageSize).subscribe(data => {
      this.cust = data;
      this.applyFilter();
    });
  }

  onPageSizeChange(pageSize: string) {
    console.log('onPageSizeChange called with pageSize:', pageSize);
    this.paginationandsort.pageSize = parseInt(pageSize, 10);
    this.page = 0;
    this.getCustomers();
  }

  custdetails(cin: number) {
    this.router.navigate(['custdetails', cin]);
  }

  handlePageChange(e: any) {
    console.log('handlePageChange called with e:', e);
    this.page = e;
    this.getCustomers();
  }

  openDetailsModal(customer: Customers) {
    console.log('openDetailsModal called with customer:', customer);
    const modalRef = this.modalService.open(CustDetailsComponent, { size: 'lg' });
    modalRef.componentInstance.cust = customer;
  }

  openComptesModal(customerId: number) {
    console.log('openComptesModal called with customerId:', customerId);
    const modalRef = this.modalService.open(CpannelComponent, { size: 'lg' });
    modalRef.componentInstance.customerId = customerId;
  }

  applyFilter() {
    this.custservice.filterCustomers(
      this.filters.firstName || 'null',
      this.filters.lastName || 'null',
      this.filters.cin || 'null',
      this.filters.sexe || 'null',
      this.filters.tel || 'null',
      this.filters.email || 'null',
      this.filters.agence || 'null',
      this.filters.profession || 'null',
  
    ).subscribe(filteredData => {
      this.filteredCustomers = filteredData;
    });
 
  }
  applyFilterKeyword() {
    this.custservice.filterCustomersByKeyword(this.searchQuery).subscribe(filteredData => {
      this.filteredCustomers = filteredData;
    });
  }
  clearSearch() {
    this.searchQuery = '';
    this.filters = {
      firstName: '',
      lastName: '',
      email: '',
      cin: '',
      tel: '',
      sexe: '',
      profession: '',
      agence: '',
     
    };
    this.applyFilter();
  }
  addCompte(cin: string) {
    this.custservice.addCompte(cin).subscribe(
      (response) => {
        console.log('Compte ajouté avec succès', response);
        Swal.fire('Salut !', 'Compte ajouté avec succès', 'success')
      
      },
      (error) => {
        console.error('Error adding compte:', error);
        Swal.fire(':/', 'Error adding compte:', 'error')
      }
    );
  }
}
