import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustService } from '../core/services/cust.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-sold',
  templateUrl: './add-sold.component.html',
  styleUrls: ['./add-sold.component.scss']
})
export class AddSoldComponent implements OnInit {
  rib: string = '';
  montant: number = 0;

  constructor(private custService: CustService) { }

  ngOnInit(): void {
  }

  onAddSolde(): void {
    if (this.rib && this.montant > 0) {
      this.custService.addSolde(this.rib, this.montant).subscribe(
        response => {
          
          console.log(response);
        },
        error => {
         
          console.error(error);
        }
      );
    } else {
      
      console.error("RIB et Montant sont requis.");
    }
  }
}