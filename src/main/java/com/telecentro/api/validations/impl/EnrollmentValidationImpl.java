package com.telecentro.api.validations.impl;

import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.validations.EnrollmentValidation;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentValidationImpl implements EnrollmentValidation {
    @Override
    public void validate(Course course) {
        if (course.getStudents().size() == 16) {
            throw new IllegalStateException("Course is full");
        }
    }
}
