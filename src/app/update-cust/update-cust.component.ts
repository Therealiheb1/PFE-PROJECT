import { Component, OnInit } from '@angular/core';
import { CustService } from '../cust.service';

@Component({
  selector: 'app-update-cust',
  templateUrl: './update-cust.component.html',
  styleUrls: ['./update-cust.component.scss']
})
export class UpdateCustComponent implements OnInit {
  customers!: any[];
  selectedCustomer: any;
  constructor(private customerService: CustService) {}

  ngOnInit() {
    this.loadCustomers();
  }
  loadCustomers() {
    this.customerService.getCustomersList()
      .subscribe(
        response => {
          this.customers = response;
        },
        error => {
          console.error(error);
        }
      );
  }
  editCustomer(customer: any) {
    this.selectedCustomer = { ...customer };
  }
  updateCustomer() {
    this.customerService.updateCustomer(this.selectedCustomer.cin, this.selectedCustomer)
      .subscribe(
        response => {
          console.log(response);
          this.selectedCustomer = null;
          this.loadCustomers();
        },
        error => {
          console.error(error);
        }
      );
  }

}
