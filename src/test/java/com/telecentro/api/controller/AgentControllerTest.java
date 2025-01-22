package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.agent.AgentRequest;
import com.telecentro.api.domain.dto.agent.LoginResponse;
import com.telecentro.api.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgentControllerTest {

    @Mock
    private AgentService agentService;

    @InjectMocks
    private AgentController agentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signup_ShouldReturnOk_WhenAgentIsRegistered() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");

        // Act
        ResponseEntity<Void> response = agentController.signup(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(agentService, times(1)).registerAgent(request);
    }

    @Test
    void login_ShouldReturnOkWithToken_WhenCredentialsAreValid() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");
        LoginResponse loginResponse = new LoginResponse("token");
        when(agentService.loginAgent(request)).thenReturn(loginResponse);

        // Act
        ResponseEntity<LoginResponse> response = agentController.login(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
        verify(agentService, times(1)).loginAgent(request);
    }

}