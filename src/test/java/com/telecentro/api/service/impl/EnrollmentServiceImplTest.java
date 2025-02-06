package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.course.CourseRequest;
import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.infra.mail.MailService;
import com.telecentro.api.repository.CourseRepository;
import com.telecentro.api.service.StudentService;
import com.telecentro.api.validations.EnrollmentValidation;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private StudentService studentService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MailService mailService;

    @Mock
    private EnrollmentValidation enrollmentValidation;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void addStudentToCourse_ShouldAddStudentAndSendEmail() throws MessagingException {
        // Arrange
        Long courseId = 1L;
        StudentRequest studentRequest = new StudentRequest(
                "John Doe",
                "1234567890",
                LocalDate.of(1990, 5, 15),
                "123 Main Street, Apt 4B, Some City",
                "johndoe@example.com",
                "11987654321"
        );
        CourseRequest request = new CourseRequest(
                "Course Name",
                "Course Description",
                LocalDate.of(2022, 12, 12),
                LocalDate.of(2022, 12, 22),
                LocalTime.of(9, 0),
                LocalTime.of(17, 0)
        );
        Course course = new Course(request);
        Student student = new Student(studentRequest);

        course.addStudent(student);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentService.saveStudent(studentRequest)).thenReturn(student);

        // Act
        enrollmentService.enrollStudent(courseId, studentRequest);

        // Assert
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentService, times(1)).saveStudent(studentRequest);
        verify(courseRepository, times(1)).save(course);
        verify(mailService, times(1)).sendMail(anyString(), anyString());
    }

}