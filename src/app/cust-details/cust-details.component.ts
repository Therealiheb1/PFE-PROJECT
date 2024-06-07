import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Customers } from '../core/models/auth.models';
import { CustService } from '../core/services/cust.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cust-details',
  templateUrl: './cust-details.component.html',
  styleUrls: ['./cust-details.component.scss']
})
export class CustDetailsComponent implements OnInit {
  @Input() cust: Customers;
  updateSuccess: boolean = false;
  updateFailed: boolean = false;
  customerForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private custservice: CustService,
    private activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.customerForm = this.formBuilder.group({
      firstName: [this.cust.firstName, Validators.required],
      lastName: [this.cust.lastName, Validators.required],
      cin: [{ value: this.cust.cin, disabled: true }, [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      tel: [this.cust.tel, [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      email: [{ value: this.cust.email, disabled: true }, [Validators.required, Validators.email]],
      dateN: [{ value: this.cust.dateN, disabled: true }, Validators.required],
      sexe: [{ value: this.cust.sexe, disabled: true }, Validators.required],
      profession: [this.cust.profession, Validators.required],
      agence: [{ value: this.cust.agence, disabled: true }, Validators.required], 
    });
  }

  updateCustomer(): void {
    if (this.customerForm.invalid) {
      return;
    }

    const updatedCust = {
      ...this.cust,
      ...this.customerForm.getRawValue(),
    };

    this.custservice.updateCustomers(parseInt(updatedCust.cin, 10), updatedCust).subscribe(
      () => {
        this.updateSuccess = true;
        Swal.fire({
          icon: 'success',
          title: 'Mise à jour réussie !',
          text: 'La mise à jour du client a été effectuée avec succès.',
        });
        setTimeout(() => {
          this.updateSuccess = false;
        }, 3000);
      },
      () => {
        this.updateFailed = true;
        Swal.fire({
          icon: 'error',
          title: 'Échec de la mise à jour',
          text: 'Veuillez réessayer.',
        });
        setTimeout(() => {
          this.updateFailed = false;
        }, 3000);
      }
    );
  }
}
