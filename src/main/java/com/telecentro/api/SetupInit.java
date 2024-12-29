package com.telecentro.api;

import com.telecentro.api.domain.dto.agent.AgentRequest;
import com.telecentro.api.infra.mail.MailService;
import com.telecentro.api.repository.AgentRepository;
import com.telecentro.api.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SetupInit implements CommandLineRunner {

    @Autowired
    private AgentService service;
    @Autowired
    private AgentRepository repository;
    @Autowired
    private MailService mailService;
    @Value("${agent.email}")
    private String email;
    @Value("${agent.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByEmail(email).isEmpty()) {
            this.service.registerAgent(new AgentRequest(email, password));
        }
        log.info("Welcome to Telecentro API");
    }
}
