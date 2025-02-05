package com.telecentro.api.service;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    List<StudentResponse> findAll();
    StudentResponse findStudentById(UUID id);
    StudentResponse updateStudent(UUID id, StudentRequest request);
    Student findStudentEntityById(UUID id);
    Student saveStudent(StudentRequest request);
    void confirmPresence(UUID id);
}
