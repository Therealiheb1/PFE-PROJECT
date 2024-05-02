package com.example.GestionDesComptesClients.Security;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class PathBasedConfigResolver implements KeycloakConfigResolver {

    @Autowired
    private Environment env;

    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        String path = request.getURI();
        String[] paths = path.split("/");
        String realmName = (paths.length > 1) ? paths[1] : "defaultRealm";// defaultRealm is a fallback

        String abtRealmIssuerUri = env.getProperty("http://localhost:8080/realms/ABT_Realm" + realmName);
        String abtRealmJwkSetUri = env.getProperty("http://localhost:8080/realms/ABT_Realm/protocol/openid-connect/certs" + realmName);
        if (abtRealmIssuerUri != null && abtRealmJwkSetUri != null) {
            InputStream abtRealmConfigStream = getClass().getResourceAsStream("/ABT_Realm.json");
            return KeycloakDeploymentBuilder.build(abtRealmConfigStream);

        } else {
            String externalAbtRealmIssuerUri = env.getProperty("http://localhost:8080/realms/EXTERNAL_ABT" + realmName);
            String externalAbtRealmJwkSetUri = env.getProperty("http://localhost:8080/realms/EXTERNAL_ABT/protocol/openid-connect/certs" + realmName);
            if (externalAbtRealmIssuerUri != null && externalAbtRealmJwkSetUri != null) {
                InputStream externalAbtRealmConfigStream = getClass().getResourceAsStream("/EXTERNAL_ABT.json");
                return KeycloakDeploymentBuilder.build(externalAbtRealmConfigStream);

            } else {
                String configPathPrefix = "classpath:/resources/keycloak/";
                String configPath = configPathPrefix + realmName + ".json";
                InputStream is = getClass().getResourceAsStream(configPath);
                if (is == null) {
                    throw new RuntimeException("Keycloak configuration not found for realm: " + realmName);
                }
                return KeycloakDeploymentBuilder.build(is);
            }
        }
    }
}