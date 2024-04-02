import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../user.service';

@Component({
  selector: 'app-adminc',
  templateUrl: './adminc.component.html',
  styleUrls: ['./adminc.component.scss']
})
export class AdmincComponent{
  f: FormGroup;

  constructor(private formBuilder: FormBuilder, private userService: UserService) {}

  ngOnInit() {
    this.f = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      RealmRoles: ['', Validators.required],

    });
  }
  
  role: string[] = [
    "",
    "Admin",
    "User" 
  ];
  onSubmit() {
    const user = this.f.value;
    this.userService.createUser(user)
      .subscribe(response => {
          console.log('User created successfully!', response);
      }, error => {
          console.error('Error creating user:', error);
      });
  }
}

