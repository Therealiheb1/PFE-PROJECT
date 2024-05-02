//package com.example.GestionDesComptesClients.Security;
//
//import org.keycloak.adapters.KeycloakConfigResolver;
//import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
//import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import javax.ws.rs.HttpMethod;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  {
//
//    @Bean
//    @Lazy
//    public KeycloakConfigResolver keycloakConfigResolver(){
//        return new PathBasedConfigResolver();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        KeycloakAuthenticationProvider authenticationProvider = new KeycloakAuthenticationProvider();
//        authenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        auth.authenticationProvider(authenticationProvider);
//    }
//
//    @Bean
//    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
//        KeycloakAuthenticationProvider authenticationProvider = new KeycloakAuthenticationProvider();
//        authenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//        return authenticationProvider;
//    }
//
//    @Bean
//    public SessionRegistry sessionReg() {
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    protected SessionAuthenticationStrategy sessionAuthStrategy() {
//        return new RegisterSessionAuthenticationStrategy(sessionReg());
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        http
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers(new AntPathRequestMatcher(HttpMethod.OPTIONS))
//                .permitAll()
//                .anyRequest().authenticated()
//                );
//        http
//                .oauth2ResourceServer(oauth -> oauth
//                        .jwt(Customizer.withDefaults())
//                );
//        return http.build();
//    }
//
//}
//
//
