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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Student student1 = new Student(new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        ));

        Student student2 = new Student(new StudentRequest(
                "Jane Doe",
                "0987654321",
                LocalDate.of(1992, 8, 25),
                "456 Elm Street, Apt 2A, Another City",
                "janedoe@example.com",
                "11876543210"
        ));

        Page<Student> studentPage = new PageImpl<>(List.of(student1, student2));
        Pageable pageable = PageRequest.of(1, 10);

        when(studentRepository.findAll(pageable)).thenReturn(studentPage);

        // Act
        Page<StudentResponse> responses = studentService.findAll(1, 10);

        // Assert
        assertEquals(2, responses.getContent().size());
        assertEquals("John Doe", responses.getContent().get(0).name());
        assertEquals("Jane Doe", responses.getContent().get(1).name());
        verify(studentRepository, times(1)).findAll(pageable);
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