package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.agent.AgentRequest;
import com.telecentro.api.domain.dto.agent.LoginResponse;
import com.telecentro.api.domain.entities.Agent;
import com.telecentro.api.domain.exceptions.CredentialsException;
import com.telecentro.api.domain.exceptions.UniqueException;
import com.telecentro.api.infra.security.TokenService;
import com.telecentro.api.repository.AgentRepository;
import com.telecentro.api.service.AgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository repository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerAgent(AgentRequest request) {
        var existsEmail = this.repository.findByEmail(request.email());

        if (existsEmail.isPresent()) {
            log.error("Email is already registered");
            throw new UniqueException("Email is already registered");
        }

        Agent agent = Agent.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        log.info("Saving agent");

        this.repository.save(agent);
        log.info("Agent saved");
    }

    @Override
    public LoginResponse loginAgent(AgentRequest request) {
        log.info("Autenticando usuário com email: {}", request.email());

        Agent agent = this.repository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado com email: {}", request.email());
                    return new CredentialsException("Email ou senha inválidos");
                });
        log.info("Usuário encontrado");

        if (!this.passwordEncoder.matches(request.password(), agent.getPassword())) {
            log.error("Senha inválida para o usuário com email: {}", request.email());
            throw new CredentialsException("Email ou senha inválidos");
        }

        String token = this.tokenService.generateToken(agent.getEmail());
        log.info("Token gerado para usuário com email: {}", agent.getEmail());

        return new LoginResponse(token);
    }
}
