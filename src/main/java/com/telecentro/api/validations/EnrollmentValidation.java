package com.telecentro.api.validations;

import com.telecentro.api.domain.entities.Course;

public interface EnrollmentValidation {
    void validate(Course course);
}
