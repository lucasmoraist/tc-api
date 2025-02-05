package com.telecentro.api.service;

import com.telecentro.api.domain.dto.agent.AgentRequest;
import com.telecentro.api.domain.dto.agent.LoginResponse;

public interface AgentService {
    void registerAgent(AgentRequest request);
    LoginResponse loginAgent(AgentRequest request);
}
