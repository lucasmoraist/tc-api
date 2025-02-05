package com.telecentro.api.service;

import com.telecentro.api.domain.dto.course.CourseDetailsResponse;
import com.telecentro.api.domain.dto.course.CourseRequest;
import com.telecentro.api.domain.dto.course.CourseResponse;
import com.telecentro.api.domain.dto.course.ListCoursesResponse;
import com.telecentro.api.domain.dto.student.StudentRequest;

import java.util.List;

public interface CourseService {
    List<ListCoursesResponse> findAllCourses();
    List<CourseDetailsResponse> findAllCoursesDetails();
    CourseDetailsResponse courseById(Long id);
    CourseResponse createCourse(CourseRequest request);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
    void addStudentToCourse(Long courseId, StudentRequest studentRequest);
}
