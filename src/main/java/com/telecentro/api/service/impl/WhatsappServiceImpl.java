package com.telecentro.api.service.impl;

import com.telecentro.api.domain.model.WhatsAppMessageRequest;
import com.telecentro.api.repository.client.WaClient;
import com.telecentro.api.service.WhatsappService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsappServiceImpl implements WhatsappService {

    private final WaClient waClient;

    @Value("${wa.api.token}")
    private String token;

    @Override
    public ResponseEntity<String> sendMessage(String phoneNumber) {
        WhatsAppMessageRequest request = new WhatsAppMessageRequest();
        request.setTo("55"+phoneNumber);
        request.setTemplate(new WhatsAppMessageRequest.Template("hello_world", new WhatsAppMessageRequest.Language("en_US")));
        log.info("Sending message to {}", request.getTo());

        log.info(token);

        return waClient.sendMessage(
                "Bearer " + token,
                request
        );
    }
}
