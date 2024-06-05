import { Component, OnInit } from '@angular/core';
import { CustService } from '../core/services/cust.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-tran',
  templateUrl: './tran.component.html',
  styleUrls: ['./tran.component.scss']
})
export class TranComponent  {
    senderRib: string = '';
    receiverRib: string = '';
    value: number = 0;
  
    constructor(private custService: CustService) {}
  
    transferFunds() {
      Swal.fire({
        title: "Es-tu sûr?",
        text: "",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Oui"
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: "Non",
            text: "Votre transaction a ete anuller",
            icon: "success"
            
          });
          this.custService.transfer(this.senderRib, this.receiverRib, this.value).subscribe(
            response => {
                console.log('Transfer successful:', response);
               
            },
            error => {
                console.error('Transfer failed:', error);
                Swal.fire(':/', 'Erreur:', 'error');
            }
        );
        }
      });
      
  }
  
    
  }