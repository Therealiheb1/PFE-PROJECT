import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.scss']
})

/**
 * Ecommerce orders component
 */
export class OrdersComponent implements OnInit {

  // bread crumb items
  breadCrumbItems: Array<{}>;
  term: any;

  transactions;

  constructor() { }

  ngOnInit() {
    this.breadCrumbItems = [{ label: 'Transaction' }, { label: 'Orders', active: true }];

    this.transactions = [
      {
        id: '#SK2540',
        name: 'Neal Matthews',
        date: '07 Oct, 2019',
        total: '$400',
   
        index: 1,
      },
      {
        id: '#SK2541',
        name: 'Jamal Burnett',
        date: '07 Oct, 2019',
        total: '$380',
    
        index: 2,
      },
      {
        id: '#SK2542',
        name: 'Juan Mitchell',
        date: '06 Oct, 2019',
        total: '$384',
      
        index: 3,
      },
      {
        id: '#SK2543',
        name: 'Barry Dick',
        date: '05 Oct, 2019',
        total: '$412',
   
        index: 4,
      },
      {
        id: '#SK2544',
        name: 'Ronald Taylor',
        date: '04 Oct, 2019',
        total: '$404',
  
        index: 5,
      },
      {
        id: '#SK2545',
        name: 'Jacob Hunter',
        date: '04 Oct, 2019',
        total: '$392',
     
        index: 6,
      },
      {
        id: '#SK2546',
        name: 'William Cruz',
        date: '03 Oct, 2019',
        total: '$374',
  
        index: 7,
      },
      {
        id: '#SK2547',
        name: 'Dustin Moser',
        date: '02 Oct, 2019',
        total: '$350',

        index: 8,
      },
      {
        id: '#SK2548',
        name: 'Clark Benson',
        date: '01 Oct, 2019',
        total: '$345',

        index: 9,
      },
    ];
  }
}
