import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CalendarComponent } from './calendar/calendar.component';
import { ChatComponent } from './chat/chat.component';

import { DefaultComponent } from './dashboards/default/default.component';
import { FilemanagerComponent } from './filemanager/filemanager.component';
import { ComptesComponent } from '../comptes/comptes.component';
import { AdminPannelComponent } from '../admin-pannel/admin-pannel.component';
import { AddUserComponent } from '../add-user/add-user.component';
import { AddAccountComponent } from '../add-account/add-account.component';
import { CustDetailsComponent } from '../cust-details/cust-details.component';
import { TranComponent } from '../tran/tran.component';
import { BoxiconsComponent } from './icons/boxicons/boxicons.component';
import { AcceuilComponent } from '../acceuil/acceuil.component';
import { AddSoldComponent } from '../add-sold/add-sold.component';
import { ChequeComponent } from '../cheque/cheque.component';
import { AuthGuard } from './utility/app.guard';
// import { AuthGuard } from '../pages/utility/app.guard';




const routes: Routes = [
  { path: '', redirectTo: 'acceuil' },
//,canActivate: [AuthGuard], data: { roles: ['superROLE_Admin'] } 
  { path: 'dashboard', component: DefaultComponent },
  { path: 'custdetails', component: CustDetailsComponent  ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'comptes', component: ComptesComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'addsolde', component: AddSoldComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'cheque', component: ChequeComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'Admin', component: AdminPannelComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin','ROLE_superAdmin'] } },
  { path: 'tran', component: TranComponent,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] }  },
  { path: 'acceuil', component: AcceuilComponent  ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'box', component: BoxiconsComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'calendar', component: CalendarComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'chat', component: ChatComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_admin'] } },
  { path: 'adduser', component: AddUserComponent ,canActivate: [AuthGuard], data: { roles: ['ROLE_superAdmin'] } },
  { path: 'addacc', component: AddAccountComponent,canActivate: [AuthGuard], data: { roles: ['ROLE_admin','ROLE_superAdmin'] } },


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule { }
