package com.telecentro.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String generateToken(String email) {
        if (this.token == null || this.token.isEmpty()) {
            log.error("Secret Key not configured");
            throw new RuntimeException("Secret Key not configured");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.token);
            String token = JWT.create()
                    .withIssuer("tc-api")
                    .withSubject(email)
                    .sign(algorithm);
            log.info("Token generated for email: {}", email);
            return token;
        } catch (JWTCreationException e) {
            log.error("Error generating token", e);
            throw new RuntimeException("Error generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.token);
            String subject = JWT.require(algorithm)
                    .withIssuer("tc-api")
                    .build()
                    .verify(token)
                    .getSubject();
            log.info("Token validated for email: {}", subject);

            return subject;
        } catch (Exception e) {
            log.error("Error validating token", e);
            throw new RuntimeException("Error validating token", e);
        }
    }

    public String recoverLoggedEmail() {
        String loggedEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (loggedEmail.equals("anonymousUser")) {
            log.error("User not logged in");
            throw new RuntimeException("User not logged in");
        }
        return loggedEmail;
    }

}
