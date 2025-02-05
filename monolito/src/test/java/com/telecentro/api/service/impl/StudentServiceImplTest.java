package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnListOfStudentResponses() {
        // Arrange
        StudentRequest student1 = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );

        StudentRequest student2 = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );

        List<Student> students = List.of(new Student(student1), new Student(student2));
        when(studentRepository.findAll()).thenReturn(students);

        // Act
        List<StudentResponse> responses = studentService.findAll();

        // Assert
        assertEquals(2, responses.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void findStudentById_ShouldReturnStudentResponse_WhenStudentExists() {
        // Arrange
        StudentRequest request = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );
        UUID id = UUID.randomUUID();
        Student student = new Student(request);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act
        StudentResponse response = studentService.findStudentById(id);

        // Assert
        assertNotNull(response);
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void findStudentById_ShouldThrowException_WhenStudentDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> studentService.findStudentById(id));
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudentResponse() {
        // Arrange
        UUID id = UUID.randomUUID();
        StudentRequest request = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );        Student student = new Student();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act
        StudentResponse response = studentService.updateStudent(id, request);

        // Assert
        assertNotNull(response);
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void saveStudent_ShouldReturnExistingStudent_WhenEmailAndRgExist() {
        // Arrange
        StudentRequest request = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );
        Student student = new Student();
        when(studentRepository.findByEmailAndRg(request.email(), request.rg())).thenReturn(Optional.of(student));

        // Act
        Student savedStudent = studentService.saveStudent(request);

        // Assert
        assertNotNull(savedStudent);
        verify(studentRepository, times(1)).findByEmailAndRg(request.email(), request.rg());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void saveStudent_ShouldCreateNewStudent_WhenEmailAndRgDoNotExist() {
        // Arrange
        StudentRequest request = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );
        when(studentRepository.findByEmailAndRg(request.email(), request.rg())).thenReturn(Optional.empty());

        // Act
        Student savedStudent = studentService.saveStudent(request);

        // Assert
        assertNotNull(savedStudent);
        verify(studentRepository, times(1)).findByEmailAndRg(request.email(), request.rg());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void confirmPresence_ShouldSetConfirmedToTrue() {
        // Arrange
        UUID id = UUID.randomUUID();
        Student student = new Student();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        // Act
        studentService.confirmPresence(id);

        // Assert
        assertTrue(student.isConfirmed());
        verify(studentRepository, times(1)).findById(id);
        verify(studentRepository, times(1)).save(student);
    }

}