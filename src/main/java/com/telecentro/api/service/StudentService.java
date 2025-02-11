package com.telecentro.api.service;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Student;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    Page<StudentResponse> findAll(int page, int size);
    StudentResponse findStudentById(UUID id);
    StudentResponse findByTerm(String term);
    StudentResponse updateStudent(UUID id, StudentRequest request);
    Student findStudentEntityById(UUID id);
    Student saveStudent(StudentRequest request);
    void confirmPresence(UUID id);
}
