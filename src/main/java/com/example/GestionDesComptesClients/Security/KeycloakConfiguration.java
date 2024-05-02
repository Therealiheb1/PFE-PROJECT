package com.example.GestionDesComptesClients.Security;

import lombok.Setter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    @Setter
    private Map<String, String> realmNames;

    private static final String KEYCLOAK_CONFIG_PATH = "classpath:/resources/keycloak/";

    public Keycloak getKeycloakInstance(String targetRealm) throws IOException {
        String realmName = realmNames.get(targetRealm);
        if (realmName == null) {
            throw new IOException("Realm " + targetRealm + " not found");
        }
        Resource resource = resourceLoader.getResource(KEYCLOAK_CONFIG_PATH + targetRealm + ".json");
        if (!resource.exists()) {
            throw new IOException("Configuration for realm " + targetRealm + " not found");
        }
        return buildKeycloakFromConfig(resource);
    }

    private Keycloak buildKeycloakFromConfig(Resource resource) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> configMap = mapper.readValue(resource.getInputStream(), Map.class);  // Read as a Map

        return KeycloakBuilder.builder()
                .serverUrl(configMap.get("authServerUrl"))
                .realm(configMap.get("realmName"))
                .clientId(configMap.get("clientId"))
                .username(configMap.get("username"))
                .password(configMap.get("password"))
                .build();
    }
    public List<String> getRealmNames() throws IOException {
        Resource[] resources = new Resource[]{resourceLoader.getResource(KEYCLOAK_CONFIG_PATH + "*.json")};
        List<String> realmNames = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Resource resource : resources) {
            String realmName = mapper.readValue(resource.getInputStream(), String.class);
            realmNames.add(realmName);
        }
        return realmNames;
    }

}
//    @Value("${realm-name}")
//    private String realm;
//
//    @Value("${realm-server-url}")
//    private String serverUrl;
//
//    @Value("${realm-client-id}")
//    private String clientId;
//
//    @Value("${realm-grant-type}")
//    private String grantType;
//
//    @Value("${Username}")
//    private String username;
//
//    @Value("${Password}")
//    private String password;

//    public Keycloak getKeycloakInstance() {
//        if (keycloak == null) {
//            keycloak = KeycloakBuilder.builder()
//                    .serverUrl(serverUrl).realm(realm).clientId(clientId)
//                    .grantType(grantType).username(username).password(password)
//                    .build();
//        }
//        return keycloak;
//    }