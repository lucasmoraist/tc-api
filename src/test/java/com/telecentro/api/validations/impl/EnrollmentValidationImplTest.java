package com.telecentro.api.validations.impl;

import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrollmentValidationImplTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentValidationImpl enrollmentValidation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void validate_ShouldThrowException_WhenCourseIsFull() {
        // Arrange
        Course course = new Course();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Student student = new Student();
            student.setConfirmed(true);
            students.add(student);
        }
        course.setStudents(students);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> enrollmentValidation.validate(course));
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void validate_ShouldNotThrowException_WhenCourseIsNotFull() {
        // Arrange
        Course course = new Course();
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            student.setConfirmed(true);
            students.add(student);
        }
        course.setStudents(students);

        // Act
        enrollmentValidation.validate(course);

        // Assert
        verify(courseRepository, never()).save(course);
    }
}