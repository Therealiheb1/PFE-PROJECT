import { Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private keycloakService: KeycloakService) {}

  getUserRoles(): string[] {
    return this.keycloakService?.getUserRoles();
  }

  isAdmin(): boolean {
    return this.getUserRoles().includes('admin');
  }

  isSuperAdmin(): boolean {
    return this.getUserRoles().includes('superAdmin');
  }
}
