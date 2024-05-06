import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../user.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent  {
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
  