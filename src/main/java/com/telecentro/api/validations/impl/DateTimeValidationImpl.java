package com.telecentro.api.validations.impl;

import com.telecentro.api.domain.entities.Course;
import com.telecentro.api.domain.exceptions.SameTimeException;
import com.telecentro.api.repository.CourseRepository;
import com.telecentro.api.validations.DateTimeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateTimeValidationImpl implements DateTimeValidation {

    @Autowired
    private CourseRepository repository;

    @Override
    public void validate(Course course) {
        if (this.repository.existsByDateAndTimeOverlap(course.getStartDate(), course.getEndDate(), course.getStartTime(), course.getEndTime())) {
            throw new SameTimeException("Course already exists at same time");
        }
    }
}
