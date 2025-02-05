package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.infra.mail.MailService;
import com.telecentro.api.repository.CourseRepository;
import com.telecentro.api.service.EnrollmentService;
import com.telecentro.api.service.StudentService;
import com.telecentro.api.validations.DateTimeValidation;
import com.telecentro.api.validations.EnrollmentValidation;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final StudentService studentService;
    private final CourseRepository courseRepository;
    private final EnrollmentValidation enrollmentValidation;
    private final MailService mailService;

    @Value("${app.host}")
    private String host;

    @CacheEvict(value = "course", allEntries = true)
    @Transactional
    @Override
    public void enrollStudent(Long courseId, StudentRequest request) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        log.info("Course found for adding student");

        this.enrollmentValidation.validate(course);

        Student student = this.studentService.saveStudent(request);
        student.setConfirmed(false);
        log.info("Student saved");

        this.courseRepository.save(course);
        log.info("Student added to course");

       try {
           String url = host + "/student/v1/confirm/" + student.getId();
           this.mailService.sendMail(student.getEmail(), url);
       } catch (MessagingException e) {
           log.error("Error sending email: {}", e.getMessage());
       }
    }
}
