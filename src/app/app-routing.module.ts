import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


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
  { path: '', component: LayoutComponent, loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule) },
  { path: 'pages', loadChildren: () => import('./extrapages/extrapages.module').then(m => m.ExtrapagesModule) },
  { path: 'crypto-ico-landing', component: CyptolandingComponent },
  { path: 'custdetails/:cin', component: CustDetailsComponent },
  { path: 'cpannel/:customerId', component: CpannelComponent },
  { path: 'dashboard', component: DefaultComponent },
  { path: 'custdetails', component: CustDetailsComponent },
  { path: 'comptes', component: ComptesComponent },
  { path: 'addsolde', component: AddSoldComponent },
  { path: 'cheque', component: ChequeComponent },
  { path: 'Admin', component: AdminPannelComponent },
  { path: 'tran', component: TranComponent },
  { path: 'acceuil', component: AcceuilComponent },
  { path: 'adduser', component: AddUserComponent },
  { path: 'addacc', component: AddAccountComponent },
  { path: '**', component: Page404Component },
  { path: 'access-denied', component: AccessDeniedComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})

export class AppRoutingModule { }
