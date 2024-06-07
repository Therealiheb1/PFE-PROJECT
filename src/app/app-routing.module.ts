import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// import { AuthGuard } from './pages/utility/app.guard';
import { LayoutComponent } from './layouts/layout.component';
import { CyptolandingComponent } from './cyptolanding/cyptolanding.component';
import { Page404Component } from './extrapages/page404/page404.component';
import { AccessDeniedComponent } from './access-denied/access-denied.component';

import { CustomerComponent } from './customer/customer.component';

const routes: Routes = [
 
  // tslint:disable-next-line: max-line-length
  { path: '', component: LayoutComponent, loadChildren: () => import('./pages/pages.module').then(m => m.PagesModule),  },
  { path: 'pages', loadChildren: () => import('./extrapages/extrapages.module').then(m => m.ExtrapagesModule),  },
  { path: 'crypto-ico-landing', component: CyptolandingComponent },
  { path: '**', component: Page404Component },
//  {
//     path: 'access-denied',
//     component: AccessDeniedComponent,
//     canActivate: [AuthGuard],
//   },
//   {
//     path: 'default',
//     component: AdminComponent,
//     canActivate: [AuthGuard],
//     // The user need to have this roles to access
//     data: { roles: ['role_default-roles'] },
//   },
  // {
  //   path: 'manager',
  //   component: CustomerComponent,
  //   canActivate: [AuthGuard],
  //   data: { roles: ['ROLE_MANAGER'] },
  // },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { scrollPositionRestoration: 'top', relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})

export class AppRoutingModule { }
