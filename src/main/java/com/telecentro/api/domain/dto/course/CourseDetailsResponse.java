package com.telecentro.api.domain.dto.course;

import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Course;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "CourseDetailsResponse", description = "Response object for course details", example = """
        {
         	"name": "Sample Event",
         	"description": "This is a sample event description.",
         	"startDate": "2024-12-27",
         	"endDate": "2024-12-28",
         	"startTime": "09:00",
         	"endTime": "17:00",
         	"students": [
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
         	]
         }
        """)
public record CourseDetailsResponse(
        String name,
        String description,
        String startDate,
        String endDate,
        String startTime,
        String endTime,
        List<StudentResponse> students
) {
    public CourseDetailsResponse(Course course) {
        this(
                course.getName(),
                course.getDescription(),
                course.getStartDate().toString(),
                course.getEndDate().toString(),
                course.getStartTime().toString(),
                course.getEndTime().toString(),
                course.getStudents()
                        .stream()
                        .map(StudentResponse::new)
                        .toList()
        );
    }
}
