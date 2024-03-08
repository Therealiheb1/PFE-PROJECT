import { KeycloakConfig } from "keycloak-js";
const keycloakConfig: KeycloakConfig={
    url: 'http://localhost:8080',
        realm: 'Client-app',
        clientId: 'cust'
};
export default keycloakConfig;