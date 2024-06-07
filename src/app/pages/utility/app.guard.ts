
import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable()
export class AuthGuard extends KeycloakAuthGuard {
  constructor(

    protected router: Router,

    protected keycloakAngular: KeycloakService

  ) {

    super(router, keycloakAngular);

  }

 

  isAccessAllowed(

    route: ActivatedRouteSnapshot,

    state: RouterStateSnapshot

  ): Promise<boolean> {
        console.log("innnnnnnnnnnnnnnnn");
        
    return new Promise((resolve, reject) => {

    let permission;

      if (!this.authenticated) {

        this.keycloakAngular.login().catch((e) => console.error(e));

        return reject(false);

      }

     

     

      const requiredRoles: string[] = route.data.roles;
        console.log("requiredRoles          ",requiredRoles);
        
      if (!requiredRoles || requiredRoles.length === 0) {
  
        permission = true;

      } else {

        if (!this.roles || this.roles.length === 0) {

        permission = false

        }
        console.log("this.roles        ",this.roles);
        console.log("this.roles[0]        ",this.roles[0]);
            
        if (requiredRoles.includes("ROLE_admin") || requiredRoles.includes("ROLE_superAdmin"))

        {
            console.log("permission          ",permission);

            permission=true;

        } else {

            permission=false;

        };

      }

      if(!permission){

          this.router.navigate(['/']);

      }

      resolve(permission)

    });

  }

}