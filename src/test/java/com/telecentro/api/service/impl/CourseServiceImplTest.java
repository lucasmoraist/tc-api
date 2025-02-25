package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.course.CourseDetailsResponse;
import com.telecentro.api.domain.dto.course.CourseRequest;
import com.telecentro.api.domain.dto.course.CourseResponse;
import com.telecentro.api.domain.dto.course.ListCoursesResponse;
import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.repository.jpa.CourseRepository;
import com.telecentro.api.validations.DateTimeValidation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CourseServiceImplTest {

    @Mock
    private DateTimeValidation dateTimeValidation;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllCourses_ShouldReturnListOfCourses() {
        // Arrange
        List<Course> courses = List.of(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<ListCoursesResponse> responses = courseService.findAllCourses();

        // Assert
        assertEquals(2, responses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void findAllCoursesDetails_ShouldReturnListOfCourseDetails() {
        // Arrange
        CourseRequest course1 = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 12, 12),
                LocalDate.of(2022, 12, 22),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );

        CourseRequest course2 = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 12, 12),
                LocalDate.of(2022, 12, 22),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );

        List<Course> courses = List.of(new Course(course1), new Course(course2));
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<CourseDetailsResponse> responses = courseService.findAllCoursesDetails();

        // Assert
        assertEquals(2, responses.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void courseById_ShouldReturnCourseDetails_WhenCourseExists() {
        // Arrange
        Long id = 1L;
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 12, 12),
                LocalDate.of(2022, 12, 22),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
        Course course = new Course(request);
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        // Act
        CourseDetailsResponse response = courseService.courseById(id);

        // Assert
        assertNotNull(response);
        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    void courseById_ShouldThrowException_WhenCourseDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> courseService.courseById(id));
        verify(courseRepository, times(1)).findById(id);
    }

    @Test
    void createCourse_ShouldSaveCourse() {
        // Arrange
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 12, 12),
                LocalDate.of(2022, 12, 22),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
        Course course = new Course(request);
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        CourseResponse response = courseService.createCourse(request);

        // Assert
        assertNotNull(response);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_ShouldUpdateExistingCourse() {
        // Arrange
        Long id = 1L;
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 12, 12),
                LocalDate.of(2022, 12, 22),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
        Course course = new Course();
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        // Act
        CourseResponse response = courseService.updateCourse(id, request);

        // Assert
        assertNotNull(response);
        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void deleteCourse_ShouldRemoveCourse() {
        // Arrange
        Long id = 1L;
        Course course = new Course();
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        // Act
        courseService.deleteCourse(id);

        // Assert
        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, times(1)).delete(course);
    }

}