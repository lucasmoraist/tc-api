package com.telecentro.api.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.telecentro.api.domain.dto.student.StudentRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_tc_student")
@Table(name = "t_tc_student")
@EqualsAndHashCode(of = "id")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 180)
    private String name;
    @Column(nullable = false, length = 10)
    private String rg;
    @Column(nullable = false)
    private LocalDate dtNasc;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, length = 255)
    private String address;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String email;
    @Column(nullable = false, length = 11)
    private String phoneNumber;

    @ManyToOne
    @JsonBackReference
    private Course course;

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student(StudentRequest request) {
        this.name = request.name();
        this.rg = request.rg();
        this.dtNasc = request.dtNasc();
        this.age = LocalDate.now().getYear() - request.dtNasc().getYear();
        this.address = request.address();
        this.email = request.email();
        this.phoneNumber = request.phoneNumber();
    }

    public void update(StudentRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }
        if (request.rg() != null) {
            this.rg = request.rg();
        }
        if (request.dtNasc() != null) {
            this.dtNasc = request.dtNasc();
            this.age = LocalDate.now().getYear() - request.dtNasc().getYear();
        }
        if (request.address() != null) {
            this.address = request.address();
        }
        if (request.email() != null) {
            this.email = request.email();
        }
        if (request.phoneNumber() != null) {
            this.phoneNumber = request.phoneNumber();
        }
    }

}
