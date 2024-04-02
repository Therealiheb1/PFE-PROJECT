import { Component, OnInit } from '@angular/core';
import { users } from '../utils/users';
import { UsersService } from '../users-service.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class EmployeeListComponent implements OnInit {

  users: users[];

  constructor(private userService: UsersService,
    private router: Router) { }

  ngOnInit(): void {
    this.getUser();
  }

  private getUser(){
    this.userService.getUsersList().subscribe(data => {
      this.users = data;
    });
  }

  userDetails(id: number){
    this.router.navigate(['user-details', id]);
  }

  updateUser(id: number){
    this.router.navigate(['update-user', id]);
  }

  
}