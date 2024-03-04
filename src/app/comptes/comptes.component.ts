import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-comptes',
  templateUrl: './comptes.component.html',
  styleUrls: ['./comptes.component.scss']
})

	
export class ComptesComponent implements OnInit {


  usersList = [
    { name: 'Hello 1', group: 'ThisType' },
    { name: 'Hello 2', group: 'ThisType' },
    { name: 'Hello 3', group: 'ThisType' },
    { name: 'Hello 4', group: 'ThisType' },
    { name: 'Hello 5', group: 'ThisType' },
    { name: 'Hello 6', group: 'ThisType' },
    { name: 'Hello 7', group: 'ThisType' },
  ];


  pages:number;
  page = 1;
  pageSize = 5;





  constructor() {
   


  }

  
  ngOnInit(): void {
    
  }
  handlePageChange(e :any){
    // this.page = e;
    console.log(e);
    this.page = e;
  }
  saveSelectedList(e :any, obj : any){
    
    
    
  }

}





export interface UserData {
  id: string;
  name: string;
  progress: string;
  color: string;
}

