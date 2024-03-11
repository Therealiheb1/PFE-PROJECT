package com.example.GestionDesComptesClients.Security;



import com.example.GestionDesComptesClients.entities.User;
import com.example.GestionDesComptesClients.service.Userservice;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


public class JwtUserSyncFilter extends OncePerRequestFilter {

    @Autowired
    private Userservice userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            String firstname = String.valueOf(token.getTokenAttributes().get("given_name"));
            String lastname = String.valueOf(token.getTokenAttributes().get("family_name"));
            String email = String.valueOf(token.getTokenAttributes().get("email"));


            User user = User.builder()
                    .nom(firstname)
                    .prenom(lastname)
                    .email(email)

                    .build();

            userService.syncUser(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to auth user");
        }

        filterChain.doFilter(request, response);
    }

}