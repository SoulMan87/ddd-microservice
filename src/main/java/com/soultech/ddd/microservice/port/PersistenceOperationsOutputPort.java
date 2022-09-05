package com.soultech.ddd.microservice.port;

import com.soultech.ddd.microservice.exception.EntityDoesNotExistError;
import com.soultech.ddd.microservice.model.Course;
import com.soultech.ddd.microservice.model.Enrollment;
import com.soultech.ddd.microservice.model.Student;

import java.util.Set;

public interface PersistenceOperationsOutputPort {

    Integer persist(Course course);

    Course obtainCourseById(Integer courseId) throws EntityDoesNotExistError;

    boolean courseExistsWithTitle(String title);

    Integer persist(Student student);

    Student obtainStudentById(Integer studentId) throws EntityDoesNotExistError;

    boolean studentExistsWithFullName(String fullName);

    Set<Enrollment> findEnrollments(Integer studentId);
}
