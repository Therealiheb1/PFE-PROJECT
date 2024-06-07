import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ChequeRequestService } from '../core/services/ChequeRequestService ';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cheque',
  templateUrl: './cheque.component.html',
  styleUrls: ['./cheque.component.scss']
})
export class ChequeComponent implements OnInit {

  requestForm: FormGroup;
  chequeStatus: string;

  constructor(
    private formBuilder: FormBuilder,
    private chequeRequestService: ChequeRequestService
  ) { }

  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({}); 
    this.getChequeStatus();
  }
  getChequeStatus() {
    this.chequeRequestService.getChequeRequestStatus().subscribe(
      status => {
        this.chequeStatus = status;
      },
      error => {
        console.error('Error fetching cheque request status', error);
        this.chequeStatus = 'Aucune demande ';
      }
    );
  }
  submit(): void {
    this.chequeRequestService.createChequeRequest().subscribe(
      response => {
        console.log('Cheque request created successfully', response);
        Swal.fire({
          icon: 'success',
          title: 'Demande de chèque créée avec succès',
          text: 'Votre demande de chèque a été créée avec succès.'
        });
      },
      error => {
        console.error('Error creating cheque request', error);
        Swal.fire({
          icon: 'error',
          title: 'Erreur lors de la création de la demande de chèque',
          text: 'Une erreur s\'est produite lors de la création de votre demande de chèque. Veuillez réessayer.'
        });
      }
    );
  }
}
