package com.telecentro.api.service.impl;

import com.telecentro.api.domain.dto.student.StudentRequest;
import com.telecentro.api.domain.dto.student.StudentResponse;
import com.telecentro.api.domain.entities.Student;
import com.telecentro.api.repository.StudentRepository;
import com.telecentro.api.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<StudentResponse> findAll(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return this.repository.findAll(pageable)
                .map(StudentResponse::new);
    }

    @Override
    public StudentResponse findStudentById(UUID id) {
        Student student = this.findStudentEntityById(id);
        log.info("Student found by id");
        return new StudentResponse(student);
    }

    @Override
    public List<StudentResponse> findByTerm(String term) {
        log.info("Searching for student by term: {}", term);
        return this.repository.findByTerm(term)
                .stream()
                .map(StudentResponse::new)
                .toList();
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

    @Transactional
    @Override
    public Student saveStudent(StudentRequest request) {
        log.info("Searching for student with email: {}", request.email());
        Optional<Student> studentFound = this.repository.findByEmailAndRg(request.email(), request.rg());

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
