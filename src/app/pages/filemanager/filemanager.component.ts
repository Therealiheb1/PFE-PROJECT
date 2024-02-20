import { Component, OnInit } from '@angular/core';
// import { HttpClient } from '@angular/common/http'; // Import HttpClient 
// import { FileSaver } from 'file-saver'; // Import FileSaver lel download

@Component({
  selector: 'app-filemanager',
  templateUrl: './filemanager.component.html',
  styleUrls: ['./filemanager.component.scss']
})
export class FilemanagerComponent implements OnInit {
  
  breadCrumbItems: Array<{}>;
  radialoptions;

  
  client = {
    fullName: 'iheb sahbeni',
    idNumber: '130291300',
    email: 'ihebsahbeni00@gmail.com',
    dateCreated: new Date('2023-12-31'),
    rib: 'FR1234567890123456789012345'
  };

  // constructor(private http: HttpClient) { } // inject l httpclient

  public isCollapsed = false;

  ngOnInit(): void {
    this.breadCrumbItems = [{ label: 'Apps' }, { label: 'File Manager', active: true }];

    // Fetch mel API
    // this.getClientData();
  }

  // Fetch client data mel api
  // getClientData() {
  //   // Replace with your API endpoint and error handling
  //   this.http.get<Client>('https://api.example.com/clients/12345')
  //     .subscribe(data => {
  //       this.client = data;
  //     });
  // }


  downloadInfo() {
    const dataStr = JSON.stringify(this.client);
    const blob = new Blob([dataStr], { type: 'application/json' });
    // FileSaver.saveAs(blob, 'client_information.json'); 
  }
}


interface Client {
  fullName: string;
  idNumber: string;
  email: string;
  dateCreated: Date;
  rib: string;
}
