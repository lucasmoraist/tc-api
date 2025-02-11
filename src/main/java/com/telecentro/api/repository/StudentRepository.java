package com.telecentro.api.repository;

import com.telecentro.api.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmailAndRg(String email, String rg);

    @Query("""
            SELECT s
            FROM t_tc_student s
            WHERE s.name LIKE %:term%
            OR s.email LIKE %:term%
            OR s.rg LIKE %:term%
            """)
    Optional<Student> findByTerm(String term);
}
