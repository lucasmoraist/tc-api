package com.telecentro.api.service;

import org.springframework.http.ResponseEntity;

public interface WhatsappService {
    ResponseEntity<String> sendMessage(String phoneNumber);
}
