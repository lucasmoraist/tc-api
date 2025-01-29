package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.course.CourseDetailsResponse;
import com.telecentro.api.domain.dto.course.CourseRequest;
import com.telecentro.api.domain.dto.course.CourseResponse;
import com.telecentro.api.domain.dto.course.ListCoursesResponse;
import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCourse_ShouldReturnOk_WhenCourseIsCreated() {
        // Arrange
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 12, 31),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
        Course course = new Course(request);
        CourseResponse response = new CourseResponse(course);
        when(courseService.createCourse(request)).thenReturn(response);

        // Act
        ResponseEntity<CourseResponse> result = courseController.createCourse(request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(courseService, times(1)).createCourse(request);
    }

    @Test
    void findAllCourses_ShouldReturnOkWithListOfCourses() {
        // Arrange

        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 12, 31),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
        Course course = new Course(request);

        List<ListCoursesResponse> responses = List.of(new ListCoursesResponse(course), new ListCoursesResponse(course));
        when(courseService.findAllCourses()).thenReturn(responses);

        // Act
        ResponseEntity<List<ListCoursesResponse>> result = courseController.findAllCourses();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
        verify(courseService, times(1)).findAllCourses();
    }

    @Test
    void findAllCoursesDetails_ShouldReturnOkWithListOfCourseDetails() {
        // Arrange
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 12, 31),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
        Course course = new Course(request);

        List<CourseDetailsResponse> responses = List.of(new CourseDetailsResponse(course), new CourseDetailsResponse(course));
        when(courseService.findAllCoursesDetails()).thenReturn(responses);

        // Act
        ResponseEntity<List<CourseDetailsResponse>> result = courseController.findAllCoursesDetails();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
        verify(courseService, times(1)).findAllCoursesDetails();
    }

    @Test
    void findCourseById_ShouldReturnOkWithCourseDetails_WhenCourseExists() {
        // Arrange
        Long courseId = 1L;
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 12, 31),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
        Course course = new Course(request);
        CourseDetailsResponse response = new CourseDetailsResponse(course);
        when(courseService.courseById(courseId)).thenReturn(response);

        // Act
        ResponseEntity<CourseDetailsResponse> result = courseController.findCourseById(courseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(courseService, times(1)).courseById(courseId);
    }

    @Test
    void findCourseById_ShouldReturnNotFound_WhenCourseDoesNotExist() {
        // Arrange
        Long courseId = 1L;
        when(courseService.courseById(courseId)).thenThrow(new EntityNotFoundException("Course not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> courseController.findCourseById(courseId));
        assertEquals("Course not found", exception.getMessage());
        verify(courseService, times(1)).courseById(courseId);
    }

    @Test
    void updateCourse_ShouldReturnOkWithUpdatedCourse_WhenCourseIsUpdated() {
        // Arrange
        Long courseId = 1L;
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 12, 31),
                LocalTime.of(8, 0),
                LocalTime.of(12, 0)
        );
        Course course = new Course(request);
        CourseResponse response = new CourseResponse(course);
        when(courseService.updateCourse(courseId, request)).thenReturn(response);

        // Act
        ResponseEntity<CourseResponse> result = courseController.updateCourse(courseId, request);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(courseService, times(1)).updateCourse(courseId, request);
    }

    @Test
    void deleteCourse_ShouldReturnNoContent_WhenCourseIsDeleted() {
        // Arrange
        Long courseId = 1L;

        // Act
        ResponseEntity<Void> result = courseController.deleteCourse(courseId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(courseService, times(1)).deleteCourse(courseId);
    }

}