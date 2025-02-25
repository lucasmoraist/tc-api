package com.telecentro.api.repository.client;

import com.telecentro.api.domain.model.WhatsAppMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "wa", url = "https://graph.facebook.com/v22.0/581474871713576/messages")
public interface WaClient {
    @PostMapping
    ResponseEntity<String> sendMessage(
            @RequestHeader("Authorization") String authorization,
            @RequestBody WhatsAppMessageRequest body
    );
}
