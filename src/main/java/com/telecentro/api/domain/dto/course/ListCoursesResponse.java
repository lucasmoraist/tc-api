package com.telecentro.api.domain.dto.course;

import com.telecentro.api.domain.entities.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ListCoursesResponse", description = "Response object to return a list of courses", example = """
        {
          "id": 1,
          "name": "Sample Event",
          "description": "This is a sample event description.",
          "startDate": "2024-12-27",
          "endDate": "2024-12-28",
          "startTime": "09:00:00",
          "endTime": "17:00:00"
        }
        """)
public record ListCoursesResponse(
        Long id,
        String name,
        String description,
        String startDate,
        String endDate,
        String startTime,
        String endTime
) {
    public ListCoursesResponse(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getStartDate() == null ? "" : course.getStartDate().toString(),
                course.getEndDate() == null ? "" : course.getEndDate().toString(),
                course.getStartTime() == null ? "" : course.getStartTime().toString(),
                course.getEndTime() == null ? "" : course.getEndTime().toString()
        );
    }
}
