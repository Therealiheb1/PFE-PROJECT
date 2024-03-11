export const environment = {
    production: false,
    serverUrl: '/api',
    keycloak: {
   
      issuer: 'http://localhost:8080/auth/',
      // Realm
      realm: 'Client-app',
      clientId: 'cust',
    },
  };