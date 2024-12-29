package com.telecentro.api.domain.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "StudentRequest", description = "Student request object", example = """
        {
          "name": "John Doe",
          "rg": "1234567890",
          "dtNasc": "1990-05-15",
          "address": "123 Main Street, Apt 4B, Some City",
          "email": "johndoe@example.com",
          "phoneNumber": "11987654321"
        }
        """)
public record StudentRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 180, message = "Name must be between 3 and 180 characters")
        String name,
        @NotBlank(message = "RG is required")
        @Size(min = 9, max = 10, message = "RG must be between 9 and 10 characters")
        String rg,
        LocalDate dtNasc,
        @NotBlank(message = "Address is required")
        @Size(min = 3, max = 255, message = "Address must be between 3 and 255 characters")
        String address,
        @Email
        @NotBlank(message = "Email is required")
        String email,
        @NotBlank(message = "Phone number is required")
        @Size(min = 11, max = 11, message = "Phone number must be 11 characters")
        String phoneNumber
) {
}
