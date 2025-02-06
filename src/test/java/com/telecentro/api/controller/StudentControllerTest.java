package com.telecentro.api.controller;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void findAll_ShouldReturnListOfStudents() {
        // Arrange
        StudentRequest studentRequest = new StudentRequest(
                "Student Name",
                "1122233344",
                LocalDate.of(2000, 1, 1),
                "Rua example",
                "johndoe@example.com",
                "11999999999"
        );
        Student student = new Student(studentRequest);
        Page<StudentResponse> students = new PageImpl<>(List.of(new StudentResponse(student), new StudentResponse(student)));
        when(studentService.findAll(1, 10)).thenReturn(students);

        // Act
        ResponseEntity<Page<StudentResponse>> response = studentController.findAll(1, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(students, response.getBody());
        verify(studentService, times(1)).findAll(1, 10);
    }

    @Test
    void findStudentById_ShouldReturnStudent_WhenStudentExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentRequest studentRequest = new StudentRequest(
                "Student Name",
                "1122233344",
                LocalDate.of(2000, 1, 1),
                "Rua example",
                "johndoe@example.com",
                "11999999999"
        );
        Student student = new Student(studentRequest);
        StudentResponse studentResponse = new StudentResponse(student);
        when(studentService.findStudentById(id)).thenReturn(studentResponse);

        // Act
        ResponseEntity<StudentResponse> response = studentController.findStudentById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentResponse, response.getBody());
        verify(studentService, times(1)).findStudentById(id);
    }

    @Test
    void findStudentById_ShouldReturnNotFound_WhenStudentDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(studentService.findStudentById(id)).thenThrow(new EntityNotFoundException("Student not found"));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> studentController.findStudentById(id));
        assertEquals("Student not found", exception.getMessage());
        verify(studentService, times(1)).findStudentById(id);
    }

    @Test
    void confirm_ShouldReturnConfirmationMessage() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        ResponseEntity<String> response = studentController.confirm(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Presença confirmada!", response.getBody());
        verify(studentService, times(1)).confirmPresence(id);
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentRequest studentRequest = new StudentRequest(
                "Student Name",
                "1122233344",
                LocalDate.of(2000, 1, 1),
                "Rua example",
                "johndoe@example.com",
                "11999999999"
        );
        Student student = new Student(studentRequest);
        StudentResponse studentResponse = new StudentResponse(student);
        when(studentService.updateStudent(id, studentRequest)).thenReturn(studentResponse);

        // Act
        ResponseEntity<StudentResponse> response = studentController.updateStudent(id, studentRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(studentResponse, response.getBody());
        verify(studentService, times(1)).updateStudent(id, studentRequest);
    }

}