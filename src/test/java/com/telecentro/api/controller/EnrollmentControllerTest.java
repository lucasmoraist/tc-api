package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private EnrollmentService enrollmentService;

    @InjectMocks
    private EnrollmentController enrollmentController;

    @Test
    void addStudentToCourse_ShouldReturnNoContent_WhenStudentIsAdded() {
        // Arrange
        Long courseId = 1L;
        StudentRequest studentRequest = new StudentRequest(
                "Student Name",
                "1122233344",
                LocalDate.of(2000, 1, 1),
                "Rua example",
                "johndoe@example.com",
                "11999999999"
        );

        // Act
        ResponseEntity<Void> result = enrollmentController.addStudentToCourse(courseId, studentRequest);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(enrollmentService, times(1)).enrollStudent(courseId, studentRequest);
    }

}