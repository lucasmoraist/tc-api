package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/student")
@Tag(name = "Student", description = "Operations related to students")
public class StudentController {

    @Autowired
    private StudentService service;

    @Operation(summary = "Find student by id", description = "Find student by id", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "id", description = "Student id", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student found successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                                "message": "Student not found",
                                "status": NOT_FOUND
                            }
                            """)
            )),
    })
    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> findStudentById(@PathVariable UUID id) {
        var response = this.service.findStudentById(id);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Confirmation of presence", description = "Confirmation of presence")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Presence confirmed successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "Presença confirmada!")
            )),
    })
    @GetMapping("/v1/confirm")
    public ResponseEntity<String> confirm() {
        return ResponseEntity.ok().body("Presença confirmada!");
    }

    @Operation(summary = "Update student", description = "Update student", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "id", description = "Student id", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Student updated successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = StudentResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "Student not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                                "message": "Student not found",
                                "status": NOT_FOUND
                            }
                            """)
            )),
    })
    @PutMapping("{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable UUID id, @RequestBody StudentRequest request) {
        var response = this.service.updateStudent(id, request);
        return ResponseEntity.ok().body(response);
    }
}
