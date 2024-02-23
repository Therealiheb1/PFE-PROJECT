import { Component, OnInit } from '@angular/core';
import { ConfigService } from '../../../core/services/config.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';




@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  constructor(private configService: ConfigService) { }

  Comptes: Array<[]>;
  ngOnInit(): void {
  }
  private fetchData() {
  

    this.configService.getConfig().subscribe(data => {
      this.Comptes = data.mcomptes;
  
    });
}
}
