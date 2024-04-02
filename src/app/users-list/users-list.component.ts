import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../core/models/auth.models';
import { UsersService } from '../users-service.service';
import { users } from '../utils/users';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit {


  users: users[];

  constructor(private usersservice: UsersService,
    private router: Router) { }

  ngOnInit(): void {
    this.getEmployees();
  }

  private getEmployees(){
    this.usersservice.getUsersList().subscribe(data => {
      this.users = data;
    });
  }

  employeeDetails(id: number){
    this.router.navigate(['user-details', id]);
  }

  updateEmployee(id: number){
    this.router.navigate(['update-user', id]);
  }

}