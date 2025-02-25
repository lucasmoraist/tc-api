package com.telecentro.api.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WhatsAppMessageRequest{
    @JsonProperty("messaging_product")
    private String messagingProduct = "whatsapp";

    private String to;
    private String type = "template";
    private Template template;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Template {
        private String name;
        private Language language;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Language {
        private String code;
    }
}
