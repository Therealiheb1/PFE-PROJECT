import { KeycloakService } from 'keycloak-angular';
import { environment } from './environment';

export function initializeKeycloak(keycloak: KeycloakService): () => Promise<any> {
  return (): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      try {
        await keycloak.init({
          config: {
            url: environment.keycloak.url,
            realm: environment.keycloak.realm,
            clientId: environment.keycloak.clientId,
          },
          // If set a false you cannot get any information about the user example the username
          // if you use keycloakService.getUserName() you get this error
          // User not logged in or user profile was not loaded.
          
          loadUserProfileAtStartUp: true,
          initOptions: {
      
            onLoad: 'login-required',
            checkLoginIframe: true,
            redirectUri: 'http://localhost:4200/comptes'
          },
          // By default the keycloak-angular library add Authorization: Bearer TOKEN to all http requests
          // Then to exclude a list of URLs that should not have the authorization header we need to provide  them here.
          bearerExcludedUrls: ['/assets'],
        });
        resolve(resolve);
      } catch (error) {
        reject(error); 
      }
    });
  };
}