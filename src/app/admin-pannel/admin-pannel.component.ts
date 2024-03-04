import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-pannel',
  templateUrl: './admin-pannel.component.html',
  styleUrls: ['./admin-pannel.component.scss']
})
export class AdminPannelComponent implements OnInit {


 


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

