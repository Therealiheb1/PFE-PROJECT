export const environment = {
    production: false,
    serverUrl: '/api',
    keycloak: {
   
      url: 'http://localhost:8080',
      realm: 'ABT_Realm',
      clientId: 'ABT_App'
    },
    proxyConfig: {
      "/api": {                       
        "target": "http://localhost:8181/", 
        "secure": false                 
      }
    }
  }; 