import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { APP_INITIALIZER, CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';

import { environment } from '../environments/environment';

import { NgbNavModule, NgbAccordionModule, NgbTooltipModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { ScrollToModule } from '@nicky-lenaers/ngx-scroll-to';

import { SharedModule } from './cyptolanding/shared/shared.module';

import { ExtrapagesModule } from './extrapages/extrapages.module';

import { LayoutsModule } from './layouts/layouts.module';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
// import { initFirebaseBackend } from './authUtils';
import { CyptolandingComponent } from './cyptolanding/cyptolanding.component';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';



import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { ComptesComponent } from './comptes/comptes.component';

import { NgxPaginationModule } from 'ngx-pagination';
import { AdminPannelComponent } from './admin-pannel/admin-pannel.component';
import { AddUserComponent } from './add-user/add-user.component';
import { PromiseType } from 'protractor/built/plugins';


import { ReactiveFormsModule } from '@angular/forms';


import { UsersListComponent } from './users-list/users-list.component';


import { FormsModule } from '@angular/forms';



import { AdmincComponent } from './adminc/adminc.component';
import { CustDetailsComponent } from './cust-details/cust-details.component';
import { AddAccountComponent } from './add-account/add-account.component';
import { UpdateCustComponent } from './update-cust/update-cust.component';
import { CpannelComponent } from './cpannel/cpannel.component';

import { TranComponent } from './tran/tran.component';
import { AcceuilComponent } from './acceuil/acceuil.component';
import { VirmentComponent } from './virment/virment.component';
import { AddSoldComponent } from './add-sold/add-sold.component';
import { ChangeAccountTypeComponent } from './change-account-type/change-account-type.component';
import { SupportComponent } from './support/support.component';
import { ChequeComponent } from './cheque/cheque.component';


// import { initializeKeycloak } from './pages/utility/app.init';

// Addressing the error:
export function initializeKeycloak(keycloak: KeycloakService) {
  return () => {
    return keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'ABT_Realm',
        clientId: 'ABT_App'
      },
      loadUserProfileAtStartUp: true,
      initOptions: {
        onLoad: 'login-required',
        redirectUri: 'http://localhost:4201/',
        promiseType: 'native'
      }
    });
  };
}

export function createTranslateLoader(http: HttpClient): any {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    CyptolandingComponent,
    ComptesComponent,

    AdminPannelComponent,
    AddUserComponent,

    UsersListComponent,
    AdmincComponent,
   
    CustDetailsComponent,
    AddAccountComponent,
    UpdateCustComponent,
    CpannelComponent,
    TranComponent,
    AcceuilComponent,
    VirmentComponent,
    AddSoldComponent,
    ChangeAccountTypeComponent,
    SupportComponent,
    ChequeComponent,
   

  ],
  imports: [
    NgxPaginationModule,
    BrowserModule,
    ReactiveFormsModule,
    // **KeycloakAngularModule (already imported)**
    KeycloakAngularModule, // Import KeycloakAngularModule
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
    LayoutsModule,
    AppRoutingModule,
    ExtrapagesModule,
    CarouselModule,
    NgbAccordionModule,
    NgbNavModule,
    NgbTooltipModule,
    SharedModule,
    ScrollToModule.forRoot(),
    NgbModule,

  ],

  bootstrap: [AppComponent],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    },


    // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    // { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    // { provide: HTTP_INTERCEPTORS, useClass: FakeBackendInterceptor, multi: true },
    // LoaderService,
    // { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptorService, multi: true },
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }