package com.telecentro.api.domain.dto.agent;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponse", description = "Login response", example = """
        {
        	"message": "Email or password is invalid",
        	"status": "UNAUTHORIZED"
        }
        """
)
public record LoginResponse(
        String token
) {
}
