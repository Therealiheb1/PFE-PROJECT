import { Component, Input } from '@angular/core';
import { Customers } from '../core/models/auth.models';
import { CustService } from '../core/services/cust.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-cust-details',
  templateUrl: './cust-details.component.html',
  styleUrls: ['./cust-details.component.scss']
})
export class CustDetailsComponent {
  @Input() cust: Customers;
  updateSuccess: boolean = false;
  updateFailed: boolean = false;

  constructor(private custservice: CustService, private activeModal: NgbActiveModal) {}

  updateCustomer(): void {
    this.custservice.updateCustomers(this.cust.cin, this.cust).subscribe(
      () => {
        this.updateSuccess = true;
        // Reset updateSuccess after 3 seconds
        setTimeout(() => {
          this.updateSuccess = false;
        }, 3000);
      },
      () => {
        this.updateFailed = true;
        // Reset updateFailed after 3 seconds
        setTimeout(() => {
          this.updateFailed = false;
        }, 3000);
      }
    );
  }
}
