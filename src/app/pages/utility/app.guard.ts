import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { KeycloakAuthGuard, KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard extends KeycloakAuthGuard {
  constructor(
    protected readonly router: Router,
    protected readonly keycloak: KeycloakService
  ) {
    super(router, keycloak);
  }

  public async isAccessAllowed(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ) {
    if (!this.authenticated) {
      await this.keycloak.login({
        redirectUri: 'http://localhost:4201'
      });
    }

    const requiredRoles = route.data.roles;
    const userRoles = this.roles;

    if (userRoles.includes('ROLE_admin')) {
      return true;
    }

    if (userRoles.includes('ROLE_superAdmin')) {
      if (requiredRoles instanceof Array && requiredRoles.includes('ROLE_superAdmin')) {
        return true;
      } else {
        this.router.navigate(['access-denied']);
        return false;
      }
    }

    if (!(requiredRoles instanceof Array) || requiredRoles.length === 0) {
      return true;
    }

    if (requiredRoles.every((role) => userRoles.includes(role))) {
      return true;
    } else {
      this.router.navigate(['access-denied']);
      return false;
    }
  }
}
export class AuthService {
  constructor(private keycloakService: KeycloakService) { }

  getUserRoles(): string[] {
    return this.keycloakService.getUserRoles();
  }

  isAdmin(): boolean {
    return this.getUserRoles().includes('ROLE_admin');
  }

  isSuperAdmin(): boolean {
    return this.getUserRoles().includes('ROLE_superAdmin');
  }
}
