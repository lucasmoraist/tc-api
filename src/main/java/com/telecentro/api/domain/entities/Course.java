package com.telecentro.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.telecentro.api.domain.dto.course.CourseRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_tc_course")
@Table(name = "t_tc_course")
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Student> students;

    public Course(CourseRequest request) {
        this.name = request.name();
        this.description = request.description();
        this.startDate = request.startDate();
        this.endDate = request.endDate();
        this.startTime = request.startTime();
        this.endTime = request.endTime();
    }

    public void update(CourseRequest request) {
        if(request.name() != null) {
            this.name = request.name();
        }
        if(request.description() != null) {
            this.description = request.description();
        }
        if(request.startDate() != null) {
            this.startDate = request.startDate();
        }
        if(request.endDate() != null) {
            this.endDate = request.endDate();
        }
        if(request.startTime() != null) {
            this.startTime = request.startTime();
        }
        if(request.endTime() != null) {
            this.endTime = request.endTime();
        }
    }

    public void addStudent(Student student) {
        this.students.add(student);
        student.setCourse(this);
    }
}
