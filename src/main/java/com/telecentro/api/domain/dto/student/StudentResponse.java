package com.telecentro.api.domain.dto.student;

import com.telecentro.api.domain.entities.Student;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "StudentResponse", description = "Response object for student", example = """
        {
        	"id": "608126ec-32ec-4f47-a16b-8f8b35ce523d",
        	"name": "John Doe",
        	"rg": "1234567890",
        	"dtNasc": "1990-05-15",
        	"age": 34,
        	"address": "123 Main Street, Apt 4B, Some City",
        	"email": "johndoe@example.com",
        	"phoneNumber": "11987654321"
        }
        """)
public record StudentResponse(
        UUID id,
        String name,
        String rg,
        String dtNasc,
        int age,
        String address,
        String email,
        String phoneNumber
) {
    public StudentResponse(Student student) {
        this(
                student.getId(),
                student.getName(),
                student.getRg(),
                student.getDtNasc().toString(),
                student.getAge(),
                student.getAddress(),
                student.getEmail(),
                student.getPhoneNumber()
        );
    }
}
