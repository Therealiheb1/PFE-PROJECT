import { Component, Input, OnInit } from '@angular/core';
import { compte } from '../core/models/auth.models';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustService } from '../core/services/cust.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { AddSoldComponent } from '../add-sold/add-sold.component';
import { ChangeAccountTypeComponent } from '../change-account-type/change-account-type.component';

@Component({
  selector: 'app-cpannel',
  templateUrl: './cpannel.component.html',
  styleUrls: ['./cpannel.component.scss']
})
export class CpannelComponent implements OnInit {
  @Input() customerId: number;
  filteredCompte: compte[];
  searchQuery: string = '';
  comptes: compte[];
  paginationandsort = {
    pageSize: 5,
  };
  Statuessort = {
    statue: true 
  };
  filters = {
    rib: '',
    iban: '',
    solde: null,
  };

  pages: number;
  page = 0;
  status = true;

  pageSize: number[] = [1, 3, 5, 10, 20, 30];
  Statues: Boolean[];
  stat : string[]= ["true","false"];

  constructor(
    private modalService: NgbModal,
    private custservice: CustService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    console.log('CpannelComponent initialized with customerId:', this.customerId);
    this.getComptes();
  }
  getComptes() {
    if (this.customerId) {
      console.log('getComptes called with customerId:', this.customerId, 'page:', this.page, 'pageSize:', this.paginationandsort.pageSize, 'status:', this.status);
      this.custservice.getComptesList(this.customerId, this.page, this.paginationandsort.pageSize, this.status).subscribe(
        data => {
          this.comptes = data;
          this.applyFilter();
        },
        error => {
          console.error('Error fetching comptes:', error);
        }
      );
    } else {
      console.error('Customer ID is not defined or invalid:', this.customerId);
    }
  }

  onPageSizeChange(pageSize: string) {
    this.paginationandsort.pageSize = parseInt(pageSize, 10);
    this.page = 0;
    this.getComptes();
  }

  StatuesChange(statue: string) {
    this.status = statue === 'true';
    this.page = 0;
    this.getComptes();
  }

  handlePageChange(e: any) {
    this.page = e - 1; // Page number is 0-based in backend
    this.getComptes();
  }

  applyFilter() {
    let soldeParam: string | undefined;
    if (this.filters.solde !== null) {
      soldeParam = this.filters.solde.toString();
    }
  
    this.custservice.filterCompteForCustomer(
      this.customerId,
      this.status,
      this.filters.rib,
      this.filters.iban,
      soldeParam !== undefined ? parseFloat(soldeParam) : undefined
    ).subscribe(filteredData => {
      this.filteredCompte = filteredData;
    }, error => {
      console.error('Error filtering accounts', error);
    });
  }

  archiveOrActivateAccount(rib: string) {
    Swal.fire({
      title: "Es-tu sûr?",
      text: "",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Oui",
      cancelButtonText: "Annuler" 
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: "succès",
          text: "",
          icon: "success"
        });
        this.custservice.desaOrActivateAccountByRib(rib)
          .subscribe(response => {
            console.log(response); 
          }, error => {
            console.error(error);
          });
      }
    });
  }
  addSolde(rib: string) {
    const modalRef = this.modalService.open(AddSoldComponent, { centered: true }); 
    modalRef.componentInstance.rib = rib; 
    modalRef.result.then((result) => {
      if (result === 'success') {
        
        this.getComptes();
      }
    }).catch((error) => {
      console.error('Modal dismissed:', error);
    });
  }
  changeType(rib: string) {
    const modalRef = this.modalService.open(ChangeAccountTypeComponent, { centered: true }); 
    modalRef.componentInstance.typeChanged.subscribe((newType: string) => {
      this.custservice.changeAccountType(rib, newType).subscribe(
        response => {
          console.log(response); 
         
        },
        error => {
          console.error('Error changing account type:', error);
        }
      );
    });
  }

  clearSearch() {
    this.searchQuery = '';
    this.applyFilter();
  }
}
