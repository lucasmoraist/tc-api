package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.repository.StudentRepository;
import com.telecentro.api.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository repository;

    @Override
    public List<StudentResponse> findAll() {
        return this.repository.findAll()
                .stream()
                .map(StudentResponse::new)
                .toList();
    }

    @Override
    public StudentResponse findStudentById(UUID id) {
        Student student = this.findStudentEntityById(id);
        log.info("Student found by id");
        return new StudentResponse(student);
    }

    @CacheEvict(value = "course", allEntries = true)
    @Override
    public StudentResponse updateStudent(UUID id, StudentRequest request) {
        Student student = this.findStudentEntityById(id);
        log.info("Student found for updating");

        log.info("Updating student");
        student.update(request);
        this.repository.save(student);
        log.info("Student updated successfully");

        return new StudentResponse(student);
    }

    @Override
    public Student findStudentEntityById(UUID id) {
        log.info("Searching for student with id: {}", id);
        return this.repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Student not found");
                    return new EntityNotFoundException("Student not found");
                });
    }

    @Override
    public Student saveStudent(StudentRequest request) {
        log.info("Searching for student with email: {}", request.email());
        Optional<Student> studentFound = this.repository.findByEmail(request.email());

        if (studentFound.isPresent()) {
            log.info("Student found by email");
            return studentFound.get();
        }

        log.info("Creating student");
        Student student = new Student(request);
        this.repository.save(student);
        log.info("Student created successfully");

        return student;
    }

    @Override
    public void confirmPresence(UUID id) {
        Student student = this.findStudentEntityById(id);
        log.info("Student found for confirming presence");

        student.setConfirmed(true);
        this.repository.save(student);
        log.info("Presence confirmed successfully");
    }
}
