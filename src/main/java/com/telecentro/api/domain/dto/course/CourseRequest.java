package com.telecentro.api.domain.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(name = "CourseRequest", description = "Request object to create a course", example = """
        {
          "name": "Sample Event",
          "description": "This is a sample event description.",
          "startDate": "2024-12-27",
          "endDate": "2024-12-28",
          "startTime": "09:00:00",
          "endTime": "17:00:00"
        }
        """)
public record CourseRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime
) {
}
