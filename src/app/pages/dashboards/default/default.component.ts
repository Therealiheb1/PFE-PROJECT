// default.component.ts
import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ConfigService } from '../../../core/services/config.service';
import { UserService } from 'src/app/core/services/UserService';
import { ActivatedRoute, Router } from '@angular/router';
import { TransactionComponent } from 'src/app/transaction/transaction.component';
import { CustService } from 'src/app/core/services/cust.service';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss']
})
export class DefaultComponent implements OnInit {

  @ViewChild('transactionModal') transactionModal: TemplateRef<any>;
  transactions: Array<[]>;
  userInfo: any;
  selectedRib: string;
  selectedSolde: number;

  constructor(private activatedRoute: ActivatedRoute, private modalService: NgbModal, private configService: ConfigService, private userService: UserService, private custService: CustService, private router: Router) {}

  ngOnInit() {
    this.fetchData();

    this.activatedRoute.queryParams.subscribe(params => {
      const selectedRib = params['rib'];
      if (selectedRib) {
        this.fetchAccountBalance(selectedRib);
      }
    });
  }

  private fetchData() {
    this.configService.getConfig().subscribe(data => {
      this.transactions = data.transactions;
    });
  }

  openTransactionModal() {
    const modalRef: NgbModalRef = this.modalService.open(TransactionComponent, { centered: true, size: 'lg' });
  }
  navigateToComptes() {
    this.router.navigate(['/comptes']); 
  }
  fetchAccountBalance(rib: string): void {
    this.custService.getAccountBalance(rib).subscribe(balance => {
      this.selectedRib = rib;
      this.selectedSolde = balance;
    });
  }
}
