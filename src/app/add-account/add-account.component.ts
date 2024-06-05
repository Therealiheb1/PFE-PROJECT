import { Component, NgModule, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormsModule } from '@angular/forms';
import { CustService } from '../core/services/cust.service';
import { HttpClient } from '@angular/common/http';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-add-account',
  templateUrl: './add-account.component.html',
  styleUrls: ['./add-account.component.scss'],
  providers: [DatePipe]
})
export class AddAccountComponent implements OnInit {
  customer = {
    username: '',
    password: '',
    firstName: '',
    lastName: '',
    email: '',
    tel: '',
    cin: '',
    sexe: '',
    agence: '',
    profession: '',
    dateN: '',
    realmRoles: []  
  };
  f: FormGroup;

  constructor(private service: CustService, private formBuilder: FormBuilder) {}

  agencies: string[] = ["zahrouni"];
  sexe: string[] = ["Homme", "Femme"];

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = ('0' + (date.getMonth() + 1)).slice(-2);
    const day = ('0' + date.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }

  ngOnInit() {
    this.f = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      tel: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      cin: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]],
      sexe: ['', Validators.required],
      agence: ['', Validators.required],
      profession: ['', Validators.required],
      dateN: [this.formatDate(new Date()), Validators.required],
      realmRoles: ['', Validators.required]
    });
  }

  create() {
    const user = this.f.value;
    console.log('user    ', user);
    console.log('this.customer    ', this.customer);

    this.service.createUser(this.customer).subscribe(
      response => {
        console.log(response);
        alert('Successfully created a new customer');
      },
      error => {
        console.log(error);
      }
    );
  }
}
