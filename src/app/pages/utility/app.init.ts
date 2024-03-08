
import { KeycloakService } from "keycloak-angular";

import { promise } from "protractor";


export function initializeKeycloak(keycloak: KeycloakService): () => Promise<boolean> {
 
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8080',
        realm: 'Client-app',
        clientId: 'cust'
      },
      initOptions: {
                onLoad: 'login-required',
                silentCheckSsoRedirectUri:
                  window.location.origin + '/assets/silent-check-sso.html'
              },
              // loadUserProfileAtStartUp: true
    });
}