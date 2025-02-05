package com.telecentro.api.repository;

import com.telecentro.api.domain.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
        FROM t_tc_course c
        WHERE c.startDate <= :endDate
          AND c.endDate >= :startDate
          AND c.startTime < :endTime
          AND c.endTime > :startTime
    """)
    boolean existsByDateAndTimeOverlap(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}
