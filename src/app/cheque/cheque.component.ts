import { Component, OnInit } from '@angular/core';
import { CustService } from '../core/services/cust.service';

@Component({
  selector: 'app-cheque',
  templateUrl: './cheque.component.html',
  styleUrls: ['./cheque.component.scss']
})
export class ChequeComponent implements OnInit {
  requests: any[] = [];

  constructor(private custService: CustService) { }

  ngOnInit(): void {
    this.custService.getChequeRequestsByRole().subscribe(
      data => {
        console.log('Received data:', data);
        this.requests = data;
      },
      error => {
        console.error('Error fetching cheque requests', error);
      }
    );
  }

  confirmerRequest(CustomerId: number): void {
    if (isNaN(CustomerId) || CustomerId <= 0) {
      console.error('Invalid chequeRequestId:', CustomerId);
      return;
    }
    this.custService.updateCurrentStep(CustomerId).subscribe(
      response => {
        console.log('Current step updated:', response);
        this.requests = this.requests.filter(request => request.id !== CustomerId);
      },
      error => {
        console.error('Error updating current step:', error);
      }
    );
  }
  
 

}
