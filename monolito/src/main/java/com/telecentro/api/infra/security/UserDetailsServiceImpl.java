package com.telecentro.api.infra.security;

import com.telecentro.api.domain.entities.Agent;
import com.telecentro.api.repository.AgentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AgentRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching for user with email: {}", username);
        Agent agent = this.repository.findByEmail(username)
                .orElseThrow(() -> {
                    log.error("User not found");
                    return new UsernameNotFoundException("User not found");
                });
        return new UserDetailsImpl(agent);
    }
}
