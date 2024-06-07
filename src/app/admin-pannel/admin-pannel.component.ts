import { Component, OnInit } from '@angular/core';
import { Customers } from '../core/models/auth.models';
import { CustService } from '../core/services/cust.service';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustDetailsComponent } from '../cust-details/cust-details.component';
import { CpannelComponent } from '../cpannel/cpannel.component';
import Swal from 'sweetalert2';
import { finalize } from 'rxjs/operators'; 

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
    agence: '',
    searchQuery: ''
  };
  loading: boolean = false;

  constructor(private modalService: NgbModal, private custservice: CustService, private router: Router) {}
  totalPages: number = 0;
  page: number = 0;
  pageSize: number[] = [1, 3, 5, 10, 20, 30];

  ngOnInit(): void {
    // this.getCustomers();
  }

  goToPage(page: number) {
    this.page = page;
    this.applyFilters();
  }

  get pages(): number[] {
    const pages = [];
    if (this.totalPages <= 5) {
      for (let i = 0; i < this.totalPages; i++) {
        pages.push(i);
      }
    } else {
      if (this.page < 3) {
        for (let i = 0; i < 5; i++) {
          pages.push(i);
        }
      } else if (this.page >= this.totalPages - 3) {
        for (let i = this.totalPages - 5; i < this.totalPages; i++) {
          pages.push(i);
        }
      } else {
        for (let i = this.page - 2; i <= this.page + 2; i++) {
          pages.push(i);
        }
      }
    }
    return pages;
  }

  applyFilters() {
    const params = {
      firstName: this.filters.firstName,
      lastName: this.filters.lastName,
      email: this.filters.email,
      cin: this.filters.cin,
      tel: this.filters.tel,
      sexe: this.filters.sexe,
      profession: this.filters.profession,
      agence: this.filters.agence,
      keyword: this.searchQuery
    };
  
    console.log('Search Parameters:', params); 
    
    this.custservice.filterCustomers(params, this.page, this.paginationandsort.pageSize).subscribe(
      (data: any) => {
        console.log('Filtered Customers:', data); 
        this.filteredCustomers = data.content;
      },
      (error) => {
        console.error('Error filtering customers:', error);
      }
    );
  }

  onPageSizeChange(pageSize: string) {
    console.log('onPageSizeChange called with pageSize:', pageSize);
    this.paginationandsort.pageSize = parseInt(pageSize, 10);
    this.page = 0;
    // this.getCustomers();
  }

  custdetails(cin: number) {
    this.router.navigate(['custdetails', cin]);
  }

  handlePageChange(e: any) {
    console.log('handlePageChange called with e:', e);
    this.page = e;
    // this.getCustomers();
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

  clearSearch() {
    
    this.filters = {
      firstName: '',
      lastName: '',
      email: '',
      cin: '',
      tel: '',
      sexe: '',
      profession: '',
      agence: '',
      searchQuery : ''
    };
  }

  addCompte(cin: string) {
    this.loading = true; 

    this.custservice.addCompte(cin)
      .pipe(
        finalize(() => { this.loading = false; }) 
      )
      .subscribe(
        (response) => {
          console.log('Compte ajouté avec succès', response);
          Swal.fire('Salut !', 'Compte ajouté avec succès', 'success');
        },
        (error) => {
          console.error('Error adding compte:', error);
          Swal.fire(':/', 'Error adding compte:', 'error');
        }
      );
  }
}
