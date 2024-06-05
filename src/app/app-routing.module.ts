import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AuthGuard } from './pages/utility/app.guard';
import { LayoutComponent } from './layouts/layout.component';
import { CyptolandingComponent } from './cyptolanding/cyptolanding.component';
import { Page404Component } from './extrapages/page404/page404.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { CustDetailsComponent } from './cust-details/cust-details.component';
import { CustomerComponent } from './customer/customer.component';
import { TranComponent } from './tran/tran.component';

import { CpannelComponent } from './cpannel/cpannel.component';
import { DefaultComponent } from './pages/dashboards/default/default.component';
import { ComptesComponent } from './comptes/comptes.component';
import { AddSoldComponent } from './add-sold/add-sold.component';
import { ChequeComponent } from './cheque/cheque.component';
import { AdminPannelComponent } from './admin-pannel/admin-pannel.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { AddUserComponent } from './add-user/add-user.component';
import { AddAccountComponent } from './add-account/add-account.component';

const routes: Routes = [
  { path: '', component: LayoutComponent, loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule), canActivate: [AuthGuard] },
  { path: 'pages', loadChildren: () => import('./extrapages/extrapages.module').then(m => m.ExtrapagesModule), canActivate: [AuthGuard] },
  { path: 'crypto-ico-landing', component: CyptolandingComponent, canActivate: [AuthGuard] },
  { path: 'custdetails/:cin', component: CustDetailsComponent, canActivate: [AuthGuard] },
  { path: 'cpannel/:customerId', component: CpannelComponent, canActivate: [AuthGuard] },
  { path: 'dashboard', component: DefaultComponent, canActivate: [AuthGuard], data: { roles: ['ROLE_superAdmin'] } },
  { path: 'custdetails', component: CustDetailsComponent, canActivate: [AuthGuard] },
  { path: 'comptes', component: ComptesComponent, canActivate: [AuthGuard] },
  { path: 'addsolde', component: AddSoldComponent, canActivate: [AuthGuard] },
  { path: 'cheque', component: ChequeComponent, canActivate: [AuthGuard] },
  { path: 'Admin', component: AdminPannelComponent, canActivate: [AuthGuard] },
  { path: 'tran', component: TranComponent, canActivate: [AuthGuard] },
  { path: 'acceuil', component: AcceuilComponent, canActivate: [AuthGuard] },
  { path: 'adduser', component: AddUserComponent, canActivate: [AuthGuard] },
  { path: 'addacc', component: AddAccountComponent, canActivate: [AuthGuard], data: { roles: ['ROLE_superAdmin'] } },
  { path: '**', component: Page404Component, canActivate: [AuthGuard] },
  { path: 'access-denied', component: AccessDeniedComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})

export class AppRoutingModule { }
