import { Component, OnInit, ViewChild } from '@angular/core';
import { emailSentBarChart, monthlyEarningChart } from './data';
import { ChartType } from './dashboard.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { EventService } from '../../../core/services/event.service';

import { ConfigService } from '../../../core/services/config.service';


@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.scss']
})
export class DefaultComponent implements OnInit {

  isVisible: string;


  amount: number = 0;
  recipientId: string = '';
  transactions: Array<[]>;
  statData: Array<[]>;

  isActive: string;





  @ViewChild('content') content;
  constructor(private modalService: NgbModal, private configService: ConfigService, private eventService: EventService) {
  }

  ngOnInit() {

    /**
     * horizontal-vertical layput set
     */
     const attribute = document.body.getAttribute('data-layout');

     this.isVisible = attribute;
     const vertical = document.getElementById('layout-vertical');
     if (vertical != null) {
       vertical.setAttribute('checked', 'true');
     }
     if (attribute == 'horizontal') {
       const horizontal = document.getElementById('layout-horizontal');
       if (horizontal != null) {
         horizontal.setAttribute('checked', 'true');
         console.log(horizontal);
       }
     }

    //  //el modal
    //  this.openModal() {
    //   this.modalService.open(this.content, { centered: true });
    // }
    // submitTransaction(this.amount: number, this.recipientId: String) {
    //   //el code
  
    //   console.log(`Transaction details: Amount: ${this.amount}, Recipient ID: ${this.recipientId}`);
  

    //   this.amount = 0;
    //   this.recipientId = '';
    // }

    /**
     * Fetches the data
     */
    this.fetchData();
  }

  

  /**
   * Fetches the data
   */
  private fetchData() {
  

    this.isActive = 'year';
    this.configService.getConfig().subscribe(data => {
      this.transactions = data.transactions;
  
    });
  }

  openModal() {
    this.modalService.open(this.content, { centered: true });
  }
   changeLayout(layout: string) {
    this.eventService.broadcast('changeLayout', layout);
  }
}
