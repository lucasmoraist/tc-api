package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enrollment")
@Tag(name = "Enrollment", description = "Operations related to enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService service;

    @Operation(summary = "Add a student to a course", description = "Add a student to a course")
    @Parameter(name = "courseId", description = "Course ID", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Student added to course successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = """
                            {
                                "message": "Course not found",
                                "status": NOT_FOUND
                            }
                            """)
            )),
    })
    @PatchMapping("/v1/course/{courseId}")
    public ResponseEntity<Void> addStudentToCourse(@PathVariable Long courseId, @RequestBody @Valid StudentRequest studentRequest) {
        this.service.enrollStudent(courseId, studentRequest);
        return ResponseEntity.noContent().build();
    }

}
