package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.agent.AgentRequest;
import com.telecentro.api.domain.dto.agent.LoginResponse;
import com.telecentro.api.domain.entities.Agent;
import com.telecentro.api.domain.exceptions.CredentialsException;
import com.telecentro.api.domain.exceptions.UniqueException;
import com.telecentro.api.infra.security.TokenService;
import com.telecentro.api.repository.jpa.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class AgentServiceImplTest {

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AgentServiceImpl agentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerAgent_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");
        when(agentRepository.findByEmail(request.email())).thenReturn(Optional.of(new Agent()));

        // Act & Assert
        assertThrows(UniqueException.class, () -> agentService.registerAgent(request));
        verify(agentRepository, never()).save(any(Agent.class));
    }

    @Test
    void registerAgent_ShouldSaveAgent_WhenEmailDoesNotExist() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");
        when(agentRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        // Act
        agentService.registerAgent(request);

        // Assert
        verify(agentRepository, times(1)).save(any(Agent.class));
    }

    @Test
    void loginAgent_ShouldThrowException_WhenEmailNotFound() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");
        when(agentRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CredentialsException.class, () -> agentService.loginAgent(request));
    }

    @Test
    void loginAgent_ShouldThrowException_WhenPasswordDoesNotMatch() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");
        Agent agent = new Agent();
        agent.setPassword("encodedPassword");
        when(agentRepository.findByEmail(request.email())).thenReturn(Optional.of(agent));
        when(passwordEncoder.matches(request.password(), agent.getPassword())).thenReturn(false);

        // Act & Assert
        assertThrows(CredentialsException.class, () -> agentService.loginAgent(request));
    }

    @Test
    void loginAgent_ShouldReturnToken_WhenCredentialsAreValid() {
        // Arrange
        AgentRequest request = new AgentRequest("test@example.com", "password");
        Agent agent = new Agent();
        agent.setEmail("test@example.com");
        agent.setPassword("encodedPassword");
        when(agentRepository.findByEmail(request.email())).thenReturn(Optional.of(agent));
        when(passwordEncoder.matches(request.password(), agent.getPassword())).thenReturn(true);
        when(tokenService.generateToken(agent.getEmail())).thenReturn("token");

        // Act
        LoginResponse response = agentService.loginAgent(request);

        // Assert
        assertEquals("token", response.token());
    }

}