package com.telecentro.api.validations.impl;

import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.repository.CourseRepository;
import com.telecentro.api.validations.EnrollmentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentValidationImpl implements EnrollmentValidation {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void validate(Course course) {
        long studentsConfirmed = course.getStudents().stream()
                .filter(Student::isConfirmed)
                .count();

        if (studentsConfirmed >= 16) {
            course.setOpen(false);
            this.courseRepository.save(course);
            throw new RuntimeException("Course is full");
        }
    }
}
