import { KeycloakService } from 'keycloak-angular';

import { environment } from 'src/environments/environment';



export function initializer(keycloak: KeycloakService) {

  return () => {

    return new Promise(async (resolve, reject) => {

      try {

        await keycloak.init({

          config: {

                    url: 'http://localhost:8080',
                    realm: 'ABT_Realm',
                    clientId: 'ABT_App'

          },

          loadUserProfileAtStartUp: true,

          enableBearerInterceptor: true,

          initOptions: {

            onLoad: 'check-sso',

            silentCheckSsoRedirectUri:

              window.location.origin + '/assets/silent-check-sso.html',

            promiseType: "native",

            redirectUri: "http://localhost:4201/",

            checkLoginIframe: false

          },

          bearerPrefix: 'Bearer'

 

        }).then(

          authenticated => {

            const keycloakAuth = keycloak.getKeycloakInstance();

            keycloakAuth.onAuthRefreshError=() =>{

            keycloak.logout()

            }

            resolve(authenticated);

          }).catch(

            error => {

              console.log(error);

              reject(error);

            }

          );

      } catch (error) {

        reject(error);

      }

    })

  }

}