package com.telecentro.api.service;

import com.telecentro.api.domain.dto.student.StudentRequest;

public interface EnrollmentService {
    void enrollStudent(Long courseId, StudentRequest request);
}
