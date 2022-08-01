package com.soultech.ddd.microservice.port;

import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.Enrollment;
import com.soultech.ddd.microservice.model.Student;

import java.util.Set;

public interface PersistenceOperationOutputPort {

    Integer persist(Course course);

    Course obtainCourseById(Integer id);

    boolean courseExistWithTitle(String title);

    Integer persist(Student student);

    Student obtainStudentById(Integer studentId);

    boolean studentExistsWithFullName(String fullName);

    Set<Enrollment> findEnrollments(Integer studentId);
}
