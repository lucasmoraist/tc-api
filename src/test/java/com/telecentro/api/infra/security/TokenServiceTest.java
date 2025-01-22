package com.telecentro.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenService();
        tokenService.setToken("secret");
    }

    @Test
    void generateToken_ShouldReturnToken_WhenEmailIsValid() {
        // Arrange
        String email = "test@example.com";

        // Act
        String token = tokenService.generateToken(email);

        // Assert
        assertNotNull(token);

        // Decode and verify the token using the JWT library
        Algorithm algorithm = Algorithm.HMAC256("secret"); // Use a chave correta
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("tc-api")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);

        assertEquals("tc-api", decodedJWT.getIssuer());
        assertEquals(email, decodedJWT.getSubject());
    }

    @Test
    void generateToken_ShouldThrowException_WhenSecretKeyNotConfigured() {
        // Arrange
        tokenService.setToken("");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> tokenService.generateToken("test@example.com"));
        assertEquals("Secret Key not configured", exception.getMessage());
    }

    @Test
    void validateToken_ShouldReturnEmail_WhenTokenIsValid() {
        // Arrange
        String email = "test@example.com";
        String token = tokenService.generateToken(email);

        // Act
        String validatedEmail = tokenService.validateToken(token);

        // Assert
        assertEquals(email, validatedEmail);
    }

    @Test
    void validateToken_ShouldThrowException_WhenTokenIsInvalid() {
        // Arrange
        String token = "invalidToken";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> tokenService.validateToken(token));
        assertEquals("Error validating token", exception.getMessage());
    }

    @Test
    void recoverLoggedEmail_ShouldReturnEmail_WhenUserIsLoggedIn() {
        // Arrange
        String email = "test@example.com";
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String loggedEmail = tokenService.recoverLoggedEmail();

        // Assert
        assertEquals(email, loggedEmail);
    }

    @Test
    void recoverLoggedEmail_ShouldThrowException_WhenUserIsNotLoggedIn() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("anonymousUser");
        SecurityContextHolder.setContext(securityContext);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> tokenService.recoverLoggedEmail());
        assertEquals("User not logged in", exception.getMessage());
    }

}