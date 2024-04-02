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
        
         
          loadUserProfileAtStartUp: true,
          initOptions: {
      
            onLoad: 'login-required',
            checkLoginIframe: true,
            redirectUri: 'http://localhost:4200/comptes'
          },
         
          bearerExcludedUrls: ['/assets'],
        });
        resolve(resolve);
      } catch (error) {
        reject(error); 
      }
    });
  };
  console.log(this.url+"   "+this.realm+"          "+this.clientId);
  
}