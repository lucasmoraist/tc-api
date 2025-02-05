package com.telecentro.api.domain.dto.course;

import com.telecentro.api.domain.entities.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CourseResponse", description = "Response object to return a course", example = """
        {
          "name": "Sample Event",
          "description": "This is a sample event description.",
          "startDate": "2024-12-27",
          "endDate": "2024-12-28",
          "startTime": "09:00:00",
          "endTime": "17:00:00"
        }
        """)
public record CourseResponse(
        String name,
        String description,
        String startDate,
        String endDate,
        String startTime,
        String endTime
) {
    public CourseResponse(Course course) {
        this(
                course.getName(),
                course.getDescription(),
                course.getStartDate().toString(),
                course.getEndDate().toString(),
                course.getStartTime().toString(),
                course.getEndTime().toString()
        );
    }
}
