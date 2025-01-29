package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.course.CourseDetailsResponse;
import com.telecentro.api.domain.dto.course.CourseRequest;
import com.telecentro.api.domain.dto.course.CourseResponse;
import com.telecentro.api.domain.dto.course.ListCoursesResponse;
import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@Tag(name = "Course", description = "Operations related to courses")
public class CourseController {

    @Autowired
    private CourseService service;

    @Operation(summary = "Create a new course", description = "Create a new course", security = {
            @SecurityRequirement(name = "bearer")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course created successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseResponse.class)
            )),
    })
    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody @Valid CourseRequest request) {
        var response = this.service.createCourse(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "List all courses", description = "List all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses listed successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListCoursesResponse.class)
            )),
    })
    @GetMapping("v1")
    public ResponseEntity<List<ListCoursesResponse>> findAllCourses() {
        var response = this.service.findAllCourses();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "List all courses details", description = "List all courses details", security = {
            @SecurityRequirement(name = "bearer")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Courses details listed successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseDetailsResponse.class)
            )),
    })
    @GetMapping
    public ResponseEntity<List<CourseDetailsResponse>> findAllCoursesDetails() {
        var response = this.service.findAllCoursesDetails();
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Find a course by ID", description = "Find a course by ID", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "courseId", description = "Course ID", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course found successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseDetailsResponse.class)
            )),
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
    @GetMapping("{courseId}")
    public ResponseEntity<CourseDetailsResponse> findCourseById(@PathVariable Long courseId) {
        var response = this.service.courseById(courseId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update a course", description = "Update a course", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "courseId", description = "Course ID", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course updated successfully", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseResponse.class)
            )),
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
    @PutMapping("{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long courseId, @RequestBody CourseRequest request) {
        var response = this.service.updateCourse(courseId, request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Delete a course", description = "Delete a course", security = {
            @SecurityRequirement(name = "bearer")
    })
    @Parameter(name = "courseId", description = "Course ID", example = "1", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
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
    @DeleteMapping("{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        this.service.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }

}
