package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.agent.AgentRequest;
import com.telecentro.api.domain.dto.agent.LoginResponse;
import com.telecentro.api.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Tag(name = "Agent", description = "Agent operations")
public class AgentController {

    @Autowired
    private AgentService service;

    @Operation(summary = "Register a new agent", description = "Register a new agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agent registered successfully", content = {}),
            @ApiResponse(responseCode = "409", description = "Email is already registered", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                            	"message": "Email is already registered",
                            	"status": "CONFLICT"
                            }
                            """)
            ))
    })
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody AgentRequest request) {
        this.service.registerAgent(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Login agent", description = "Login agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agent logged in successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                                "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQGV4YW1wbGUuY29tIn0.7J1z"
                            }
                            """)
            )),
            @ApiResponse(responseCode = "401", description = "Email or password is invalid", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)
            ))
    })
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> login(@RequestBody AgentRequest request) {
        var response = this.service.loginAgent(request);
        return ResponseEntity.ok().body(response);
    }

}
