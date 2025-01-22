package com.telecentro.api.validations.impl;

import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.exceptions.SameTimeException;
import com.telecentro.api.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class DateTimeValidationImplTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private DateTimeValidationImpl dateTimeValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_ShouldThrowException_WhenCourseTimeOverlaps() {
        // Arrange
        Course course = new Course();
        course.setStartDate(LocalDate.of(2024, 12, 27));
        course.setEndDate(LocalDate.of(2024, 12, 28));
        course.setStartTime(LocalTime.of(9, 0));
        course.setEndTime(LocalTime.of(17, 0));

        when(courseRepository.existsByDateAndTimeOverlap(
                course.getStartDate(), course.getEndDate(), course.getStartTime(), course.getEndTime()))
                .thenReturn(true);

        // Act & Assert
        assertThrows(SameTimeException.class, () -> dateTimeValidation.validate(course));
        verify(courseRepository, times(1)).existsByDateAndTimeOverlap(
                course.getStartDate(), course.getEndDate(), course.getStartTime(), course.getEndTime());
    }

    @Test
    void validate_ShouldNotThrowException_WhenCourseTimeDoesNotOverlap() {
        // Arrange
        Course course = new Course();
        course.setEndTime(LocalTime.of(17, 0));

        when(courseRepository.existsByDateAndTimeOverlap(
                course.getStartDate(), course.getEndDate(), course.getStartTime(), course.getEndTime()))
                .thenReturn(false);

        // Act
        dateTimeValidation.validate(course);

        // Assert
        verify(courseRepository, times(1)).existsByDateAndTimeOverlap(
                course.getStartDate(), course.getEndDate(), course.getStartTime(), course.getEndTime());
    }
}