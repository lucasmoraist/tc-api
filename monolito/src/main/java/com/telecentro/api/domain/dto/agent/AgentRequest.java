package com.telecentro.api.domain.dto.agent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "AgentRequest", description = "Agent request", example = """
        {
        	"email": "johndoe@example.com",
        	"password": "123456789"
        }
        """)
public record AgentRequest(
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Password is required")
        String password
) {
}
