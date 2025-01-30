package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.course.CourseDetailsResponse;
import com.telecentro.api.domain.dto.course.CourseRequest;
import com.telecentro.api.domain.dto.course.CourseResponse;
import com.telecentro.api.domain.dto.course.ListCoursesResponse;
import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.infra.mail.MailService;
import com.telecentro.api.repository.CourseRepository;
import com.telecentro.api.service.CourseService;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final DateTimeValidation dateTimeValidation;

    @Cacheable("course")
    @Override
    public List<ListCoursesResponse> findAllCourses() {
        log.info("Listing all courses");
        return this.repository.findAll()
                .stream()
                .map(ListCoursesResponse::new)
                .toList();
    }

    @Override
    public List<CourseDetailsResponse> findAllCoursesDetails() {
        log.info("Listing all courses with details");
        return this.repository.findAll()
                .stream()
                .map(CourseDetailsResponse::new)
                .toList();
    }

    @Override
    public CourseDetailsResponse courseById(Long id) {
        Course course = this.getCourseById(id);
        log.info("Course found by id");
        return new CourseDetailsResponse(course);
    }

    @CacheEvict(value = "course", allEntries = true)
    @Override
    public CourseResponse createCourse(CourseRequest request) {
        log.info("Creating new course");
        Course course = new Course(request);

        this.dateTimeValidation.validate(course);

        this.repository.save(course);
        log.info("Course created");
        return new CourseResponse(course);
    }

    @CacheEvict(value = "course", allEntries = true)
    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = this.getCourseById(id);
        log.info("Course found for updating");

        log.info("Updating course");
        course.update(request);
        this.repository.save(course);
        log.info("Course updated");

        return new CourseResponse(course);
    }

    @CacheEvict(value = "course", allEntries = true)
    @Override
    public void deleteCourse(Long id) {
        Course course = this.getCourseById(id);
        log.info("Course found for deleting");

        this.repository.delete(course);
        log.info("Course deleted");
    }

    @Override
    public Course getCourseById(Long id) {
        log.info("Searching for course with id: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Course not found with id: {}", id);
                    return new EntityNotFoundException("Course not found");
                });
    }
}
